using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MusicIndexer
{
    class Program
    {
        static void Main(string[] args)
        {
            string ScanPath = string.Empty;
            string ThumbPath = string.Empty;

            for (int i = 0; i < args.Length; i++)
            {
                if (args[i].Contains("-scan"))
                {
                    ScanPath = args[i + 1];
                }

                if (args[i].Contains("-thumb"))
                {
                    ThumbPath = args[i + 1];
                }
            }

            if(string.IsNullOrEmpty(ScanPath) || string.IsNullOrEmpty(ThumbPath))
            {
                Console.WriteLine("ERROR: No Parameter -scan or -thumb specified...");
                Console.ReadKey();
                Environment.Exit(0);
            }

            Metadata m = new Metadata(ScanPath, ThumbPath);
            ElasticSearch e = new ElasticSearch();
            e.CreateEntity(m.Get());
        }
    }
}
