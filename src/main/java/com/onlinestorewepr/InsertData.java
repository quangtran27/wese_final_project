package com.onlinestorewepr;

import com.onlinestorewepr.dao.CartDAO;
import com.onlinestorewepr.dao.CategoryDAO;
import com.onlinestorewepr.dao.ProductDAO;
import com.onlinestorewepr.dao.UserDAO;
import com.onlinestorewepr.entity.Cart;
import com.onlinestorewepr.entity.Category;
import com.onlinestorewepr.entity.Product;
import com.onlinestorewepr.entity.User;

public class InsertData {
  public static void main(String[] args) {
    UserDAO userDAO = new UserDAO();
    User user = userDAO.get("admin");
    if (user == null) {
      user = new User("admin", "admin", true, "Admin", null);
      userDAO.insert(user);
    }

    User customerUser = userDAO.get("customer");
    if (customerUser == null) {
      Cart cart = new Cart();
      new CartDAO().insert(cart);
      customerUser = new User("customer", "customer", false, "Customer", cart);
      userDAO.insert(customerUser);
    }

    Category category1 = new Category("Shirt");
    Category category2 = new Category("T-Shirt");
    Category category3 = new Category("Bottom");
    Category category4 = new Category("Outerwear");
    Category category5 = new Category("Shoes");

    CategoryDAO categoryDAO = new CategoryDAO();
    categoryDAO.insert(category1);
    categoryDAO.insert(category2);
    categoryDAO.insert(category3);
    categoryDAO.insert(category4);
    categoryDAO.insert(category5);

    Product c1p1 = new Product(
        "B.D. Flannel Solid",
        "images/IMG_1209_c1ac1f84-3055-4f7c-bc8e-d02a50560005_900x.jpg",
        "<BEAMS PLUS> flannel that expresses natural stretchability with 100% cotton flannel material with processed threads\n" +
            "The brushed fabric is soft to the touch and warm\n" +
            "The pattern is based on American button-down shirts produced in the 1960s\n" +
            "All processes are completed by skilled craftsmen at factories in Japan\n" +
            "The hem on both sides has a + mark\n" +
            "Color: Grey\n" +
            "100% cotton\n" +
            "Made in Japan\n" +
            "Model is 6'3 wearing a size XL",
        159.0, 100, 0.0, "XL", "Grey", "BEAMS PLUS", true, category1);
    Product c1p2 = new Product(
        "Work Shirt",
        "images/IMG_8350_c2018262-6bb3-4de3-81d5-5d970f5b0e7d_720x.jpg",
        "Garment dyed Orange\n" +
            "Made in America\n" +
            "Model is 6'3 wearing a size M",
        259.0, 100, 0.0, "L", "Orange", "VINTAGE HEAVY", true, category1);
    Product c1p3 = new Product(
        "Brushed Chambray L/S 2 Pocket Work Shirt",
        "images/IMG_7445_78365115-ba58-4cc6-93b1-b1ffc91fe345_720x.jpg",
        "Color : Fuchsia\n" +
            "2 pockets on the chest\n" +
            "100% cotton\n" +
            "Made in USA\n" +
            "Model is 6'3 wearing a size M",
        225.0, 100, 0.0, "XL", "Fuchsia", "BEAMS PLUS", true, category1);

    // T-Shirts
    Product c2p1 = new Product(
        "Slub Yarn Sweatshirt",
        "images/DSC01176__86810.jpg",
        "Color: Greencast indigo \"Green cast indigo\", which is a mixture of indigo and green dye\n" +
            "By wearing it for a long time, it will change to a pale and gentle tone blue\n" +
            "A sweatshirt made from an original fabric that is woven from indigo rope-dyed yarn with uneven thickness\n" +
            "It is very soft and comfortable to wear because it is knitted slowly without tension\n" +
            "100% Cotton\n" +
            "Once Washed\n" +
            "Made In Japan\n" +
            "Model is 6'3\" wearing size 6",
        240.00, 100, 150.0, "XL", "Greencast Indigo", "BEAMS PLUS", true, category2);
    Product c2p2 = new Product(
        "Piquet Large Pointed Collar Retro Polo",
        "images/IMG_2514_720x.jpg",
        "Color: Beige\n" +
            "Made in Japan\n" +
            "Model is 6'3\" wearing size 4",
        304.00, 100, 0.0, "XL", "Beige", "KAPITAL", true, category2);

    // Bottoms
    Product c3p1 = new Product(
        "255-ASKS 17oz Natural Indigo x Kakishibu Regular Straight",
        "images/IMG_4537_720x.jpg",
        "Color: Natural Indigo (Hank Dyed) x Kakishibu (Persimmon)\n" +
            "Comes with Oni Denim original coin purse\n" +
            "Regular Straight fit\n" +
            "17oz Oni Original Japanese Selvedge Denim\n" +
            "Low tension weave\n" +
            "Deer leather patch\n" +
            "Oni original donut button\n" +
            "Serial number\n" +
            "One Wash\n" +
            "100% cotton\n" +
            "Made In Japan",
        815.00, 100, 700.0, "XL", "Blue", "KAPITAL", true, category3);

    Product c3p2 = new Product(
        "1108EC Slim Straight",
        "images/IMG_4065_f5e4aa40-2a1c-48ad-943e-28283087080e_720x.jpg",
        "Color: Ecru\n" +
            "Mid-High Rise\n" +
            "Fabric: 13oz Fullcount Original Japanese Selvedge Denim\n" +
            "One Wash\n" +
            "100% Organic Cotton\n" +
            "Goat Skin Leather Patch\n" +
            "100% Cotton Thread Sewing Construction\n" +
            "Made In Japan\n" +
            "Model is 5'11\" wearing size 30\n" +
            "Natural-colored organic cotton denim that is not dyed or bleached, leaving cotton waste (leaves and stem fragments), and taking advantage of the natural color of cotton and a plump texture with little damage.",
        298.00, 100, 0.0, "L", "Ecru", "KAPITAL", true, category3);
    Product c3p3 = new Product(
        "Cee Jeanss C212",
        "images/IMG_7501_7d17ddfe-83d0-4ffc-8a3c-4a4b432754b9_720x.jpg",
        "CEE JEANSS / C212\n" +
            "The back pockets are embroidered with the iconic New York skyline stitching\n" +
            "Inspired by the vintage jeans cut with a straight fit through the seat and thigh with slightly tapered leg\n" +
            "Color: Faded Black\n" +
            "Hand distressed by craftsman\n" +
            "13.5oz Japanese selvedge jeans\n" +
            "100% cotton\n" +
            "Button Fly\n" +
            "Made In Japan\n" +
            "Model is 6'3 wearing a size 32",
        330.00, 100, 0.0, "L", "Faded Black", "KAPITAL", true, category3);

    // Outerwear
    Product c4p1 = new Product(
        "11.5oz Denim BONE Lining Reversible ZIP",
        "images/IMG_7217_910379ec-4e2c-4b02-8b0a-58902ca70950_720x.jpg",
        "Color: Faded Indigo\n" +
            "Jacket with an inner blanket inspired by vintage workwear\n" +
            "relaxed silhouette\n" +
            "3 pockets on the front\n" +
            "Corduroy collar\n" +
            "Reversible. When turned inside out, there is a bone design on a blanket\n" +
            "Made In Japan\n" +
            "100% cotton\n" +
            "Lining: 45% cotton, 35% wool, 20% nylon\n" +
            "Distressed. Any tearing or damage caused by processing is the intention of the design and is not a product defect.\n" +
            "Model is 6'3\" wearing size 3",
        93.00, 100, 0.0, "L", "UP Work JKT", "KAPITAL", true, category4);
    Product c4p2 = new Product(
        "CENTURY DENIM 1st JKT - No.9+S",
        "images/h22_102-1614_tuscumbia-shirt_oatmeal_001_900x.jpg",
        "Color: No.9+S/ Mud\n" +
            "Deep brown sashiko denim\n" +
            "Mud dyed in Omami Oshima. Natural dyeing method that is practiced only in Amami Oshima.\n" +
            "Linen sashiko yarn\n" +
            "Black sashiko thread is dyed with the Amami Oshima traditional dyeing technique through a precise process by artisans.\n" +
            "Unwashed Sanforized Denim, 3% Shrinkage\n" +
            "Size5 and size6 have a T-shaped split back design\n" +
            "100% cotton\n" +
            "Made in Japan\n" +
            "Model is 6'3\" wearing size 4",
        798.00, 100, 0.0, "XL", "Black", "KAPITAL", true, category4);

    ProductDAO productDAO = new ProductDAO();
    productDAO.insert(c1p1);
    productDAO.insert(c1p2);
    productDAO.insert(c1p3);
    productDAO.insert(c2p1);
    productDAO.insert(c2p2);
    productDAO.insert(c3p1);
    productDAO.insert(c3p2);
    productDAO.insert(c3p3);
    productDAO.insert(c4p1);
    productDAO.insert(c4p2);

  }
}
