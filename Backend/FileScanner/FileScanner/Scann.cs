using System.IO;
using System.Security.Cryptography;
using System.Text;

namespace FileScanner
{
    public class Result
    {
        public string Path { get; set; }
        public string Hash { get; set; }
    }
    public class Scann
    {
        static string GetMd5Hash(MD5 md5Hash, string input)
        {

            // Convert the input string to a byte array and compute the hash.
            byte[] data = md5Hash.ComputeHash(Encoding.UTF8.GetBytes(input));

            // Create a new Stringbuilder to collect the bytes
            // and create a string.
            StringBuilder sBuilder = new StringBuilder();

            // Loop through each byte of the hashed data
            // and format each one as a hexadecimal string.
            for (int i = 0; i < data.Length; i++)
            {
                sBuilder.Append(data[i].ToString("x2"));
            }

            // Return the hexadecimal string.
            return sBuilder.ToString();
        }

        public static Result GenHash(string path)
        {
            if (new FileInfo(path).Attributes.HasFlag(FileAttributes.Hidden))
            {
                return null;
            }
            using (MD5 md5Hash = MD5.Create())
            {
                return new Result() {Path = path, Hash = GetMd5Hash(md5Hash, path)};
            }
        }
    }
}