### Product configuration

### Price Model

    - Flat, Volume, Graduated

### Tiers

* Volume or Flat pricing tiers can coexist in a product’s pricing configuration
* Graduated pricing tiers cannot coexist with other tiers which have a pricing model not equal to Graduated
* Tier ranges cannot be negative (i.e. we cannot configure a price for a negative quantity)
* Tier range s are contiguous, for example:

### Valid ranges

[[1, 10],[11, 50],[51, 100]]

### Invalid ranges

[[1, 10],[12, 50],[-7, 100]]

### Models

* Product configuration
* Price Tier
* Price Request
* Price Response
* Price Model Enum

### Service

* Product Price Service
* Product Configuration service

### Note - Graduated

* For any quantity within a tier, the total cost is similar to volume, BUT, if a user has configured multiple pricing
  tiers, a buyer can ‘graduate’ into a different price. For example,
* If a buyer wants to buy 8 units of a product, the total cost is = 8 * $100
  If the buyer wants 15 units, the cost is 10 * $100 + 5 * $150 = $1750

### Question for clarification

* What is basically range in product configuration? is it like bulk amount price? (LGTM)
* Are we going to support multi currency? Or this will be USD for now (LGTM)?
* Can price model be different in multiple tiers? (LGTM)
* Where we will get the product configuration, is this pre-configured in the system?
* What if client requested product count is not in the range of the product configuration?
* How the calculation will take place for Volume + Flat pricing tiers, if they coexist?
* Are we going to focus on developing functionality for dynamic product configuration or we will assume and set static
  product pricing config for products? (LGTM)
* Do we need to consider the multi-threading environment in terms of request processing?