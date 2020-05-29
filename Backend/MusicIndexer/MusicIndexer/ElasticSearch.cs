using Nest;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace MusicIndexer
{
    class ElasticSearch
    {
        ElasticClient client;

        public ElasticSearch()
        {
            var node = new Uri("http://localhost:9200");
            var settings = new ConnectionSettings(node);
            client = new ElasticClient(settings);
        }

        public void CreateEntity(List<Track> list)
        {
            foreach (Track i in list)
            {
                string json = string.Empty;
                try
                {
                    var response = client.Index(i, idx => idx.Index("music"));

                    if (!response.IsValid)
                    {
                        Console.WriteLine("Elastic Request Failed: "+response.DebugInformation);
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine("ERROR: " + e.Message+" Payload: "+json);
                }
            }
        }
    }
}
