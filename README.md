# GraphStream -- JavaFX Viewer

[![Build Status](https://travis-ci.org/graphstream/gs-ui-javafx.svg?branch=master)](https://travis-ci.org/graphstream/gs-ui-javafx)

The GraphStream project is a java library that provides an API to model, analyze and visualize graphs and dynamic graphs.

This module contains a JavaFX implementation of the GraphStream Viewer.

Check out the Website <http://www.graphstream-project.org/> for more information.

## UI

This viewer is a rewrite of the old [gs-ui](https://github.com/graphstream/gs-ui) [Scala](http://www.scala-lang.org/)-based renderer. `gs-ui` is now deprecated.

This renderer supports almost all of the CSS properties defined in the [GraphStream CSS Reference](http://graphstream-project.org/doc/Tutorials/GraphStream-CSS-Reference_1.0/), however it is still a work in progress. This viewer is intended only at 2D visualization actually.

We are interested in any suggestion to improve this renderer, and your use cases could be of great help for us in developing this project. If you like and use this project, this could be a good contribution.

## Install UI

`gs-ui-javafx` is a plugin to the  `gs-core` main project. 

The release comes with a pre-packaged jar file named `gs-ui-javafx.jar` that contains the GraphStream viewer classes. It depends on the root project `gs-core`. To start using GraphStream with a viewer, simply put `gs-core.jar` and `gs-ui-javafx.jar` in your class path. You can download GraphStream on the github releases pages:

- [gs-core](https://github.com/graphstream/gs-core/releases)
- [gs-ui-javafx](https://github.com/graphstream/gs-ui-javafx/releases)

Maven users, you may include `gs-core` and `gs-ui-javafx` as a dependency to your project using <https://jitpack.io>.
Simply add the `jitpack` repository to the `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

then, add the `gs-core` and `gs-ui-javafx` to your dependencies:

```xml
<dependency>
    <groupId>com.github.graphstream</groupId>
    <artifactId>gs-core</artifactId>
    <version>dev-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>com.github.graphstream</groupId>
    <artifactId>gs-ui-javafx</artifactId>
    <version>dev-SNAPSHOT</version>
</dependency>
```

You can use any version of `gs-core` and `gs-ui-javafx` you need, provided they are the same. Simply specify the desired version in the `<version>` tag. The version can be a git tag name (e.g. `2.0`), a commit number, or a branch name followed by `-SNAPSHOT` (e.g. `dev-SNAPSHOT`). More details on the [possible versions on jitpack](https://jitpack.io/#graphstream/gs-core).

## Configure UI

Finally, `gs-core` needs to be told which UI implementation to use. Simply add a system property to you project: 

```java
System.setProperty("org.graphstream.ui", "javafx");
```

Or use the command line :

```bash
java -Dorg.graphstream.ui=javafx YourClass
```

## Help

You may check the documentation on the [website](http://graphstream-project.org). You may also share your questions on the mailing list at http://sympa.litislab.fr/sympa/subscribe/graphstream-users

## License

See the COPYING file.