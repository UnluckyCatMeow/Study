using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Pr1
{
    internal class Description : IEntity
    {
        public int Id { get; set; }
        public string ProductDescription { get; set; }
        public int ProductId { get; set; }

        public Description(string productDescription, int descriptionId)
        {
            ProductDescription = productDescription;
            ProductId = descriptionId;
        }

        public override string ToString()
        {
            return $"{Id} {ProductDescription} {ProductId}";
        }
    }
}
