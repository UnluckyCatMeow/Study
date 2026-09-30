using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Pr1
{
    internal class Order : IEntity
    {
        public int Id { get; set; }
        public string NameClients { get; set; }
        public int ProductId { get; set; }
        public string Responsible { get; set; }

        public Order(string nameClients, int productId, string responsible)
        {
            NameClients = nameClients;
            ProductId = productId;
            Responsible = responsible;
        }

        public override string ToString()
        {
            return $"{Id} {NameClients} {ProductId} {Responsible}";
        }
    }
}
