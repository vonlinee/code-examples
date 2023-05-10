# TiwulFX

TiwulFX is a JavaFX component library. This version 3 is tested against OpenJDK11 and OpenJFX11. Should be working on JDK 10 as well.
The preferred IDE to run the sample projects is [NetBeans 9](https://netbeans.apache.org).

## Features

* Complete Table related components. [Demo video.](https://www.youtube.com/watch?v=X8p-QdiExbM)
* Save column sorting, position and width.
* Agile editing.
* Ready to use data-type based columns: Text, Number (Double, Integer, BigInteger etc), Lookup (object), TypeAhead (Object), TickColumn etc.
* Pagination.
* Export to Excel.
* Copy row/cell.
* Bulk paste from spreadsheet.
* Support background thread for loading and saving data.
* MessageDialog using builder pattern.
* Reorder, detach and dock tab panes. [Link to the video.](https://www.youtube.com/watch?v=q_n23Ah1ftQ)
* Localization.
* TiwulFXUtil class provides many handy methods: read-write user preference properties, Date-LocalDate conversion, open a file using native app, get local translated string, get date format based on local etc.

## Components

* TableControl
	* TextColumn
	* NumberColumn
	* TypeAheadColumn
	* LookupColumn
	* CheckBoxColumn
	* TickColumn
	* DateColumn
	* LocalDateColumn
	* TextAreaColumn
* NumberField
* TypeAheadField
* LookupField
* MessageDialog
* SideMenu
* DetachableTabPane
* DetachableTab

## Getting Started

To run the sample projects take a look at this [video](https://youtu.be/eDYf9ELdgJI) and do the followings:

* Grab the sourcecode by cloning it or download it. Check above video on how to download the sourcecode. To clone it run the following command:

```git clone https://bitbucket.org/panemu/tiwulfx.git```

* Open sample projects using [NetBeans 9](https://netbeans.apache.org).
* Then Clean and build `shared` project.
* Right click a sample project and select "Run"

To use TiwulFX dependency into your maven project, include the following dependency on your pom.xml file.

```
<dependency>
   <groupId>com.panemu</groupId>
   <artifactId>tiwulfx</artifactId>
   <version>3.0</version>
</dependency>
```

Binary jar can be downloaded in [sonatype](https://oss.sonatype.org/content/repositories/releases/com/panemu/tiwulfx/3.0/)

If you use Java 8, please use TiwulFX version 2 as follow:

```
<dependency>
   <groupId>com.panemu</groupId>
   <artifactId>tiwulfx</artifactId>
   <version>2.0</version>
</dependency>
```

## License

* TiwulFX : LGPL
* TiwulFX samples: BSD


https://github.com/panemu/tiwulfx-dock


