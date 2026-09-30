using Pr1;
using System.Text;
Console.InputEncoding = Encoding.UTF8;
Console.OutputEncoding = Encoding.UTF8;

Product product1 = new Product("Ковбаса", 200);
Product product2 = new Product("Сало", 150);
Product product3 = new Product("Згущене молоко", 70);

DBItem<Product> DBProduct = new DBItem<Product>();
DBProduct.AddItem(product1);
DBProduct.AddItem(product2);
DBProduct.AddItem(product3);

Description description1 = new Description("концентрований солодкий молочний продукт", 3);
Description description2 = new Description("м'ясний продукт із фаршу в оболонці або без неї, який пройшов термічну обробку чи ферментацію", 1);
Description description3 = new Description("це тваринний підшкірний жир, найчастіше свинячий", 2);

DBItem<Description> DBDescription = new DBItem<Description>();
DBDescription.AddItem(description1);
DBDescription.AddItem(description2);
DBDescription.AddItem(description3);

Category category1 = new Category("М'ясна продукція");
Category category2 = new Category("Товар зі знижкою");
Category category3 = new Category("Молочна продукція");

DBItem<Category> DBCategory = new DBItem<Category>();
DBCategory.AddItem(category1);
DBCategory.AddItem(category2);
DBCategory.AddItem(category3);

ProductCategory productCategory1 = new ProductCategory(1, 1);
ProductCategory productCategory2 = new ProductCategory(1, 2);
ProductCategory productCategory3 = new ProductCategory(2, 1);
ProductCategory productCategory4 = new ProductCategory(3, 2);
ProductCategory productCategory5 = new ProductCategory(3, 3);

DBItem<ProductCategory> DBProductCategory = new DBItem<ProductCategory>();
DBProductCategory.AddItem(productCategory1);
DBProductCategory.AddItem(productCategory2);
DBProductCategory.AddItem(productCategory3);
DBProductCategory.AddItem(productCategory4);
DBProductCategory.AddItem(productCategory5);

Order order1 = new Order("Ілля Негусєв", 1, "Анна Д.");
Order order2 = new Order("Іван Потопальський", 1, "Олег С.");
Order order3 = new Order("Дар'я Грищенко", 2, "Анна Д.");

DBItem<Order> DBOrder = new DBItem<Order>();
DBOrder.AddItem(order1);
DBOrder.AddItem(order2);
DBOrder.AddItem(order3);

foreach (Product product in DBProduct.Items)
{
    Console.WriteLine("Назва та опис товару:" + "\t");
    Console.WriteLine(product);

    foreach (Description description in DBDescription.Items)
    {
        if (description.ProductId == product.Id)
        {
            Console.WriteLine("\t" + description);
        }
    }
    Console.WriteLine("Замовили:" + "\t");
    foreach (Order order in DBOrder.Items)
    {
        if (order.ProductId == product.Id)
        {
            Console.WriteLine("\t" + order);
        }
    }
    Console.WriteLine("Категорія товару:" + "\t");
    foreach (ProductCategory productCategory in DBProductCategory.Items)
    {
        if (productCategory.ProductId == product.Id)
        {
            foreach (Category category in DBCategory.Items)
            {
                if (category.Id == productCategory.CategoryId)
                {
                    Console.WriteLine("\t" + category);
                }
            }
        }
    }
}

