A back-end side of a carpark project in Java 8 + Spring + Hibernate.

Assumptions:

1. For now, the only supported currency is "PLN", however there is a gateway for the future for further currency support. To add new currency support a CurrencyConverter has to be implemented for each new currency. Since the system is supposed to work in Poland, I assumed that the rates stay fixed in "PLN" and in the case of a different currency request, the whole fee is converted into the requested currency, not the rates.
2. A parking entry can be requested in two ways: either by entry id - in which case a unique entry is returned - or by license plate number - in which case the most recent parking entry for that license plate is returned, including one that may be in progress.
3. When calculating fee for parking I assume that the smallest time unit is one second, meaning that if at least one second of an hour has passed, the fee for that hour is included in the total fee for that parking entry.
