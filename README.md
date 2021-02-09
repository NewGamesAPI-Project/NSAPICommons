# NSAPI Commons: Cross-platform Minecraft Util Library
![Badge: Java](https://img.shields.io/badge/Java-8-red?style=for-the-badge)
![Version: Commons](https://img.shields.io/badge/Version-1.0-blue?style=for-the-badge)

![Supports: Spigot](https://img.shields.io/badge/Spigot-1.16.4-orange?style=for-the-badge)
![Supports: Nukkit](https://img.shields.io/badge/Nukkit-1.0.11-darkred?style=for-the-badge)

This project contains a bunch of classes, utility methods, and constants that are used across projects that fall under the NSAPI project, written in Java 8. This library is *not* meant to be packaged withing any plugins as that would lead to duplicate code. It should remain as a standalone jar.

The project is written in a way where the majority of the code is compatible across multiple Minecraft platforms such as Nukkit and Spigot with possible additions being Sponge and Forge if required.

## Components:
 - **MapRegion Data Type:** 2 corners associated with a type and properties.
 - **PointEntity Data Type:** A position associated with a type and properties.
 - **Platform Independent Log:** A very simple logging interface, translated for each platform.
 - **PosRot + Region:** Two data types that store positions.
 - **Importer: [in dev]** Assign variables inside another class if requested with an @Import
 - **Text Utilities:** Generate a random string, pick a random string out of an array, etc.
 - **Quick Immutability:** A shortened way of making a list, or a map immutable.