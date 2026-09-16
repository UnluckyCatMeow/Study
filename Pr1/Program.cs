using Pr1;

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


foreach (Product product in DBProduct.Items)
{
    Console.WriteLine(product);
    foreach (Description description in DBDescription.Items)
    {
        if (description.ProductId == product.Id)
        {
            Console.WriteLine("\t" + description);
        }
    }
    //тут буде ще один форіч, де ми пройдемся по продукт Категорі і з'язкуємо з продуктІд
}

