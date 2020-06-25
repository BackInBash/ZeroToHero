using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace FileScanner
{
    public class FileList
    {
        public string FilePath { get; set; }
        public string Hash { get; set; }
        public DateTime Date { get; set; }
    }

    public abstract class Output
    {
        public const string Name = "output.json";
        public const string MismatchName = "mismatch.json";
    }

    public class Program
    {
        public static string PATH { get; set; }
        public static bool Compare { get; set; }

        static void PrintUsage()
        {
            Console.WriteLine("Usage: FileScanner.exe -p {FilePath}\n" +
                              "    -p | --path    Path to work with\n" +
                              "    -c | --comare  Enable Compare Routine");
        }
        static void Comparer(List<FileList> LocalFileList)
        {
            if (!File.Exists(Output.Name))
            {
                Console.WriteLine("Error: No Output File Found...");
                Environment.Exit(1);
            }
            Console.WriteLine("Parse Results...");
            List<FileList> RemoteFileList = JsonConvert.DeserializeObject<List<FileList>>(File.ReadAllText(Output.Name));
            List<List<FileList>> Mismatch = new List<List<FileList>>();
            foreach (FileList File in LocalFileList)
            {
                //bool exists = RemoteFileList.Exists(x => x.Hash.Equals(File.Hash));
                var hash = from entry in RemoteFileList.AsParallel()
                    where entry.Hash.Equals(File.Hash)
                    select entry;
                
                if (hash.Count() == 0)
                {
                    try
                    {
                        Console.WriteLine("File Hash mismatch!");
                        var f = RemoteFileList.First(x => x.FilePath.Contains(File.FilePath));
                        Mismatch.Add(new List<FileList>()
                        {
                            new FileList() {FilePath = File.FilePath, Date = File.Date, Hash = File.Hash},
                            new FileList()
                                {FilePath = f.FilePath, Date = f.Date, Hash = f.Hash}
                        });
                    }
                    catch (InvalidOperationException)
                    {
                        Mismatch.Add(new List<FileList>()
                        {
                            new FileList() {FilePath = File.FilePath, Date = File.Date, Hash = File.Hash},
                            new FileList()
                                {FilePath = "File not Exists", Date = DateTime.Now, Hash = string.Empty}
                        });
                    }
                }
            }

            if (Mismatch.Count() != 0)
            {
                File.WriteAllText(Output.MismatchName, JsonConvert.SerializeObject(Mismatch, Formatting.Indented));
            }
        }
        private static void AddFiles(string path, IList<string> files)
        {
            try
            {
                Directory.GetFiles(path)
                    .ToList()
                    .ForEach(s => files.Add(s));

                Directory.GetDirectories(path)
                    .ToList()
                    .ForEach(s => AddFiles(s, files));
            }
            catch (UnauthorizedAccessException ex)
            {
                Console.WriteLine("Error: " + ex.Message, ex);
            }
        }
        
        static void Main(string[] args)
        {
            if (args.Length == 0)
            {
                Console.WriteLine("NO CLI Parameters given exit...");
                PrintUsage();
                return;
            }

            for (int i = 0; i<args.Length; i++)
            {
                switch (args[i])
                {
                    case "-p":
                        PATH = args[i+1];
                        break;
                    case "--path":
                        PATH = args[i+1];
                        break;
                    case "-c":
                        Compare = true;
                        break;
                    case "--compare":
                        Compare = true;
                        break;
                }
            }
            
            Console.WriteLine("Get all Files in Folders");
            List<string> FileList = new List<string>();
            AddFiles(PATH, FileList);

            List<Task<Result>> thread = new List<Task<Result>>();
            Console.WriteLine("Listed "+FileList.Count()+" Files.");
            
            Console.WriteLine("Start Execution...");
            foreach (string file in FileList)
            {
                Task<Result> task = Task<Result>.Run(() => Scann.GenHash(file));
                thread.Add(task);
            }
            
            Console.WriteLine("Wait for Exit...");
            do
            {
                Thread.Sleep(1000);
            } while (thread.Where(x => x.Status == TaskStatus.Running).Count() > 0);
            
            Console.WriteLine("Write Results...");
            List<FileList> files = new List<FileList>();
            
            foreach (Task<Result> Task in thread)
            {
                try
                {
                    files.Add(new FileList()
                        { FilePath = Task.Result.Path, Hash = Task.Result.Hash, Date = DateTime.Now });
                }
                catch (NullReferenceException)
                {
                    
                }
            }

            if (Compare)
            {
                Comparer(files);
            }
            else
            {
                Console.WriteLine("Save Results...");
                if (File.Exists(Output.Name))
                {
                    File.AppendAllText(Output.Name, JsonConvert.SerializeObject(files, Formatting.Indented));
                }
                else
                {
                    File.WriteAllText(Output.Name, JsonConvert.SerializeObject(files, Formatting.Indented));
                }
            }
        }
    }
}