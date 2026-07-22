# Sazzler Product Service — Remaining Work

**Overall Status: Create and Get-All are implemented with Kafka event publishing. Update, Delete, and Get-By-ID are missing. There is a logic bug in createProduct.**

---

## 1. Bug — `createProduct` Duplicate Check Always Fails

In `ProductService.createProduct()`:

```java
if (productRepo.findById(productRequest.id()) == null) { // BUG
```

`productRepo.findById()` returns `Optional<Product>`, **never null**. This check always evaluates to false, so:
- The `Product Created Successfully!` branch is **never reached**.
- `ProductIDAlreadyExists` is **always thrown**.

**Fix:**
```java
if (productRepo.findById(productRequest.id()).isEmpty()) {
```

---

## 2. Missing Endpoints — Update, Delete, Get-By-ID

`ProductController` only has `POST /api/product/create` and `GET /api/product/products`. A product catalog needs full CRUD.

**What to add:**

| Method | Path | Description |
|---|---|---|
| `GET` | `/api/product/products/{id}` | Get a single product by ID |
| `PUT` | `/api/product/products/{id}` | Update product name or price |
| `DELETE` | `/api/product/products/{id}` | Delete a product |

Each of these should also fire a Kafka event (see item 3).

---

## 3. Kafka Events Should Fire Automatically on CRUD

Currently, Kafka events are sent via a separate manual endpoint (`POST /api/product/kafka/send`). The Order Service needs to stay in sync automatically whenever a product changes.

**What to do:**
- Remove (or keep as internal-only) the manual `/kafka/send` endpoint.
- Call `productEventProducerService.sendMessage(...)` inside `ProductService.createProduct()`, and in the new `updateProduct()` and `deleteProduct()` methods.
- The `ProductEvent` should carry an `eventType` field (`CREATED`, `UPDATED`, `DELETED`) so the Order Service knows what to do.

---

## 4. `getProducts()` Returns String Instead of JSON

In `ProductController`:
```java
public String getProducts() {
    return Objects.requireNonNull(productRetrieveService.getProducts().getBody()).toString();
}
```

This calls `.toString()` on a `List<Product>`, which returns a Java object string like `[Product@1a2b3c]`, not valid JSON.

**Fix:** Change the return type to `ResponseEntity<List<Product>>` and return the service result directly:
```java
@GetMapping("/products")
public ResponseEntity<List<Product>> getProducts() {
    return productRetrieveService.getProducts();
}
```

---

## 5. No Pagination on Get-All Products

`getProducts()` returns all products with no limit. On a real catalog this will cause memory and performance problems.

**What to add:**
```java
@GetMapping("/products")
public ResponseEntity<Page<Product>> getProducts(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "20") int size
) { ... }
```

Change `ProductRepo` to extend `PagingAndSortingRepository`.

---

## 6. Product Entity — Missing Validation

The `Product` entity has no field validation. Invalid data can be saved to the database.

**What to add on `ProductRequest`:**
```java
@NotBlank String name;
@NotNull @Positive BigDecimal price;
@NotBlank String id;
```

And annotate the controller parameter with `@Valid`.

---

## 7. Tests — None

`SazzlerProductServiceApplicationTests` is an empty placeholder. No tests exist for this service.

**What to add:**

| Test class | What to cover |
|---|---|
| `ProductServiceTest` | Create success, duplicate ID (after bug fix), findById not found |
| `ProductRetrieveServiceTest` | Returns list, empty list throws `ProductNotFound` |
| `ProductControllerTest` | POST /create, GET /products, error cases |
| `ProductEventProducerServiceTest` | Message sent to correct topic, failure logged |
