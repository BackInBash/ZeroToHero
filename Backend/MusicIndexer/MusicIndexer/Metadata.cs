using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MusicIndexer
{
    class Metadata
    {
        private string SCAN_PATH;
        private string THUMB_PATH;

        public Metadata(string ScanPath, string ThumbPath)
        {
            SCAN_PATH = ScanPath;
            THUMB_PATH = ThumbPath;
        }
        private IEnumerable<FileInfo> GetFilesByExtensions(DirectoryInfo dir, params string[] extensions)
        {
            if (extensions == null)
                throw new ArgumentNullException("extensions");
            IEnumerable<FileInfo> files = dir.EnumerateFiles("*.*", SearchOption.AllDirectories);
            return files.Where(f => extensions.Contains(f.Extension));
        }

        public List<Track> Get()
        {
            List<Track> track = new List<Track>();
            foreach (var f in GetFilesByExtensions(new DirectoryInfo(SCAN_PATH), ".flac", ".mp3", ".wav"))
            {
                try
                {
                    var tfile = TagLib.File.Create(f.FullName);
                    string GUID = Guid.NewGuid().ToString();
                    string thumb = "null";

                    if (tfile.Tag.Pictures.Length != 0)
                    {
                        thumb = THUMB_PATH + "\\" + GUID + ".jpg";
                    }

                    track.Add(new Track
                    {
                        Album = tfile.Tag.Album ?? "null",
                        Artist = tfile.Tag.FirstPerformer ?? "null",
                        Bitrate = tfile.Properties.AudioBitrate.ToString() ?? "null",
                        Duration = tfile.Properties.Duration.ToString("hh\\:mm\\:ss") ?? "null",
                        FilePath = f.FullName,
                        Genre = tfile.Tag.FirstGenre ?? "null",
                        Thumbnail = thumb,
                        Titel = tfile.Tag.Title ?? "null",
                        Year = tfile.Tag.Year.ToString() ?? "null"

                    });

                    try
                    {
                        if (tfile.Tag.Pictures.Length != 0)
                        {
                            File.WriteAllBytes(thumb, tfile.Tag.Pictures[0].Data.Data);
                        }
                    }
                    catch (Exception e)
                    {
                        Console.WriteLine("Thumb-ERROR: " + e.Message, e);
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Metadata-ERROR: " + ex.Message, ex);
                }
            }
            return track;
        }
    }
}
