# Java Spring based password manager API

## About
Project is still in progress!

Password manager API designed to help you keep your online accounts safe and easily accessible.
Securely manage and protect your passwords online with online password manager.

- This project is Java Spring backend API.
- Database used in project: MySQL. 
- Frontend application is made with JavaScript and React : [Link](https://github.com/bro256/bg.manager.ui)

## Features
- **User Registration and Authentication.** Allows users to create an account and authenticate themselves securely.
- **Password Storage and Organization.** Provides the ability for users to store and organize their passwords securely in the password manager.
- **Password Generation.** Offers an advanced password generation feature that generates strong and unique passwords for users.
- **Password Generation.** Offers an advanced password generation feature that generates strong and unique passwords for users.
- **Encryption and Data Security.** Encrypts stored passwords to protect them from unauthorized access.
- **Cross-Platform Access.** Lets access passwords from desktops, laptops, and mobile devices.
- **Search.** Enables users to search their password entries.
-  **Export passwords** Allows users to export their data in CSV format to be transferred to other password managers.

## Cryptography
### Key derivation function
- **PBKDF2 (PBKDF2WithHmacSHA256)**
- **Iterations:** 100 000
- **Salt:** 128 bits (US National Institute of Standards and Technology (NIST) recommendation)
- **Generated key length:** 256 bits
- Implemented in front end app in accordance with official Crypto-js package documentation

### Symmetric encryption
- **Algorithm:** AES-256
- **Key length:** 256 bits
- **Mode:** CBC (Cipher Block Chaining)
- **Block size:** 128 bits
- **Initialization vector (IV):** 96 bits, random, different for every record
- Implemented in front end app in accordance with official Crypto-js package documentation

## Contributing
Thank you for considering contributing to our project! We welcome contributions from the community to make our project better. To contribute, please follow these guidelines:
- Fork the repository and clone it locally.
- Create a new branch for your contribution:
```
git checkout -b feature/your-feature-name
```
- Make your desired changes, additions, or bug fixes.
- Commit your changes with clear and descriptive commit messages:
```
git commit -m "Add feature: your feature description"
```
- Push your changes to your forked repository:
```
git push origin feature/your-feature-name
```
- Open a pull request (PR) against the main branch of the original repository.
- Ensure your PR includes a clear description of the changes made, along with any necessary documentation or steps to test the changes.
- We review your contribution, provide feedback, and work with you to address any necessary changes.
- Once approved, your changes will be merged into the main repository. Congratulations on your contribution!

We appreciate your valuable contributions and look forward to working together to improve our project!
