using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Pr1
{
    internal class Product : IEntity
    {
        public int Id {  get; set; }
        public string ProductName { get; set; }
        public double Price { get; set; }

        public Product(string name, double price)
        {
            this.ProductName = name;
            this.Price = price;
        }
        public override string ToString()
        {
            return $"{Id} {ProductName} {Price}";
        }
    }
}
