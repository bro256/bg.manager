# Java Spring based password manager API

## About
Project is still in progress!

Password manager API designed to help you keep your online accounts safe and easily accessible.
Securely manage and protect your passwords online with online password manager.

- This project is Java Spring backend API.
- Database used in project: MySQL. 
- Frontend application is made with JavaScript and React : [Link](https://)

## Features
- **User Registration and Authentication.** Allows users to create an account and authenticate themselves securely.
- **Password Storage and Organization.** Provides the ability for users to store and organize their passwords securely in the password manager.
- **Password Generation.** Offers an advanced password generation feature that generates strong and unique passwords for users.
- **Encryption and Data Security.** Encrypts stored passwords to protect them from unauthorized access.
- **Search.** Enables users to search their password entries.

## Cryptography
### Key derivation function
- **PBKDF2 (PBKDF2WithHmacSHA256)**
- **Iterations:** 100 000
- **Salt:** 128 bits (US National Institute of Standards and Technology (NIST) recommendation)
- **Generated key length:** 256 bits
- Implemented in accordance with official Crypto-js package

### Symmetric encryption
- **Algorithm:** AES-256
- **Key length:** 256 bits
- **Mode:** CBC (Cipher Block Chaining)
- **Block size:** 128 bits
- **Initialization vector (IV):** 96 bits, random, different for every record
- Implemented in accordance with official Crypto-js package