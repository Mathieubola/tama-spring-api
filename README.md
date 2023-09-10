# Tama Spring API

This application is a first time project. The goal was to learn Spring Framework and Java. While I acknowledge that the code is far from perfect, it represents my progress and efforts in grasping these technologies.

## Objectives

The main objective of this project was to gain hands-on experience with Spring Framework and Java programming. Specifically, I aimed to:

- Learn the fundamentals of Spring Boot.
- Understand the concepts of Dependency Injection, Inversion of Control (oC).
- Implement RESTful APIs using Spring MVC.
- Work with databases and data access using Spring Data.
- Explore the use of the Redis database.

## Known Issues

### Jackson2HashMapper

During the development of this project, I wanted to store the player object flattened, and I encountered an issue with this. Specifically, I tried to use the Jackson2HashMapper for this purpose, but it didn't work as expected. When hashing the player object, the mapper would only hash the player object and the player inventory object and not the consumable inventory for example. I believe this is because food objects are stored in lists and Jackson doesn't like it but I couldn't find an alternative so I implemented my own """hashing algorithm""" which is very bad and I should never do that but it's too late now and it works.

If you have a simpler fix I'd love to head about it.

### Weird redis key issue

When storing player object to redis, I use a key to make them identifiable. Specifically the key for a player named test would be "players:test" and this is what I give to RedisTemplate.putAll but when I look into the redis database, the key is `\xac\xed\x00\x05t\x00\rplayers:test` wich apparently is a binary representation of Java object serialization header. I don't know why, I don't know how, I just give him a String object and it mean I can't do the scan for `players:*`.

Again, if you have a fix, I would love it