#Java Spring based online password manager

##Cryptography
### Key derivation function
- PBKDF2 (PBKDF2WithHmacSHA256)
- Iterations: 600 000 (2023 OWASP recommendation)
- Salt: 128 bits (US National Institute of Standards and Technology (NIST) recommendation)
- Generated key length: 256 bits
- Implemented in accordance with official: [java.security package documentation](https://docs.oracle.com/javase/8/docs/api/java/security/package-summary.html) and [javax.crypto package documentation](https://docs.oracle.com/javase/8/docs/api/javax/crypto/package-summary.html)
