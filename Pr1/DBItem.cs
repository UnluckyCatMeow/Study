using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Pr1
{
    internal class DBItem<T> where T : IEntity
    {
        private int counter = 1;
        public List<T> Items {  get; set; } = new List<T>();
        public int AddItem(T item)
        {
            item.Id = counter++;
            Items.Add(item);
            return item.Id;
        }

    }
}
