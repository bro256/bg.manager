# Java Spring based password manager API

## About
Project is still in progress!

Password manager API designed to help you keep your online accounts safe and easily accessible.
Securely manage and protect your passwords online with online password manager.

- This project is Java Spring backend API.
- Database used in project: MySQL. 
- Frontend application is made with JavaScript and React : [Link](https://)


## Cryptography
### Key derivation function
- PBKDF2 (PBKDF2WithHmacSHA256)
- Iterations: 600 000 (2023 OWASP recommendation)
- Salt: 128 bits (US National Institute of Standards and Technology (NIST) recommendation)
- Generated key length: 256 bits
- Implemented in accordance with official: [java.security package documentation](https://docs.oracle.com/javase/8/docs/api/java/security/package-summary.html) and [javax.crypto package documentation](https://docs.oracle.com/javase/8/docs/api/javax/crypto/package-summary.html)
