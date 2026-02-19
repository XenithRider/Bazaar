# 🌿 GreenMart — Eco-Friendly Shopping Backend

A full-featured **Spring Boot REST API** for an eco-conscious e-commerce platform. GreenMart lets users shop sustainably, tracks carbon footprint per order, and empowers sellers to get eco-certification for their products — all managed through a role-based admin system.

---



## ✨ Features

- **JWT Authentication** — Stateless login/register with BCrypt password hashing
- **Role-Based Access Control** — Three roles: `ROLE_USER`, `ROLE_SELLER`, `ROLE_ADMIN`
- **Product Marketplace** — Public product listing; sellers manage their own products
- **Eco Certification Workflow** — Sellers request eco-certification; admins approve/reject
- **Cart with Eco-Swap Suggestions** — Suggests greener alternatives for non-certified cart items
- **Carbon Footprint Tracking** — Per-order carbon used/saved calculation at checkout
- **Order Management** — Checkout clears cart and creates an order with full carbon summary
- **Seller Dashboard Reports** — Revenue, eco-certified product counts, daily sales chart
- **User Dashboard Reports** — Total spend, carbon saved, weekly carbon chart, eco badges
- **Admin Dashboard** — Platform-wide stats, pending seller approvals, product certifications, CSV export
- **Seller Upgrade Flow** — Users can request seller role; admin approves via dashboard
- **Admin Upgrade Flow** — Users can request admin access; existing admins approve

---


## 📁 Project Structure

```
ecobazaar/
├── src/main/java/com/example/ecobazaar/
│   ├── config/
│   │   ├── DataLoader.java          
│   │   ├── OpenApiConfig.java       
│   │   └── SecurityConfig.java      
│   ├── controller/
│   │   ├── AuthController.java      
│   │   ├── ProductController.java   
│   │   ├── CartController.java      
│   │   ├── OrderController.java     
│   │   ├── AdminController.java     
│   │   ├── AdminRequestController.java  
│   │   ├── SellerRequestController.java 
│   │   ├── UserReportController.java    
│   │   ├── SellerReportController.java  
│   │   └── UserController.java      
│   ├── dto/                         
│   ├── model/                       
│   ├── repository/                  
│   ├── security/
│   │   └── JwtFilter.java           
│   ├── service/                     
│   └── util/
│       └── JwtUtil.java            
└── src/main/resources/
    └── application.properties     
```




## 🔑 Role & Permission System

```
ROLE_USER
  ├── Browse marketplace (public)
  ├── Add/remove items from cart
  ├── Eco-swap cart items
  ├── Checkout and place orders
  ├── View order history
  ├── View personal carbon & spend reports
  └── Request seller or admin role upgrade

ROLE_SELLER
  ├── All USER permissions (shopping side)
  ├── Create / update / delete own products
  ├── Request eco-certification for products
  └── View seller sales & revenue reports

ROLE_ADMIN
  ├── All SELLER permissions
  ├── Approve / reject eco-certification requests
  ├── Approve / reject seller role requests
  ├── Approve / reject admin role requests
  ├── View platform-wide admin reports
  └── Export orders as CSV
```

---

## 🌱 Carbon Footprint Logic

At **checkout**, for each cart item:

1. The product's `carbonImpact` value is recorded as **carbon used**.
2. The system searches for a non-eco-certified version of a similar product (by keyword matching on the product name).
3. If a higher-carbon alternative exists, the difference is counted as **carbon saved**.
4. Net carbon = `totalCarbonUsed - totalCarbonSaved` and is stored on the `Order`.

The **cart summary** also runs a real-time eco-swap suggestion: it finds the first non-certified item in the cart and suggests the closest eco-certified alternative with estimated CO₂ savings.

---

## 🏅 Eco Badges

Users and sellers earn badges based on their activity:

**User badges** (based on total net carbon footprint across all orders):

| Badge | Threshold |
|---|---|
| 🌱 Beginner Eco-Saver | carbon > 0 |
| 🛒 Conscious Shopper | carbon > 100 kg |
| 🌍 Green Hero | carbon > 200 kg |
| 🏆 Eco Legend | carbon > 500 kg |

**Seller badges** (based on revenue and eco-certified products):

| Badge | Condition |
|---|---|
| 📈 New Seller | Default |
| 🌿 Trusted Eco Seller | 10+ eco-certified products |
| 🚀 Growing Seller | Revenue > ₹50,000 |
| 🏆 Top Seller | Revenue > ₹1,00,000 and 20+ eco products |

---


## 🗄️ Database Tables

Hibernate auto-creates the following tables from the JPA entities:

| Table | Description |
|---|---|
| `users` | All users with role, eco score, and seller request status |
| `products` | Products with price, carbon impact, eco flags, and seller FK |
| `cart_item` | Cart items linked to user and product |
| `orders` | Completed orders with carbon and price totals |
| `order_items` | Line items within each order |
| `admin_requests` | Pending/processed admin role upgrade requests |

---

## 📝 License

This project is built for educational and demonstration purposes.
