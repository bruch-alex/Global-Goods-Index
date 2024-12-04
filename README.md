# Global Goods Index

***Global Goods Index*** is an application designed to help users compare the prices of various goods and services,
including
groceries, transportation, clothing, activities, dining, and utilities, across multiple countries. All prices are
standardized and displayed in U.S. dollars for consistency. By selecting one or more countries and products, users can
easily visualize price differences.

## Features

- **Price Comparison in USD:**
  Compare the prices of goods in U.S. dollars across multiple countries for a consistent overview.


- **Localization Support:**
  Supports multiple languages: **German (DE), English (ENG), and Russian (RUS).**


- **Theme Selection:** Choose from four visually distinct themes:

    - ```PrimerLight```
    - ```PrimerDark```
    - ```NordLight```
    - ```NordDark```


- **User-Friendly Visualizations:**

    - Dynamic bar charts for comparing product prices.
    - Tables for a detailed view of selected product prices by country.


- **Data Availability Notification:** Get notified when data for selected products is unavailable, helping users adjust
  their comparisons.


- **Search Functionality:** Quickly find products or countries using an intuitive search bar.


- **Settings Persistence:** Save preferences for theme and language.


- **Interactive Tooltips:** Get additional context while exploring price trends.

## Technologies Used

- [JavaFX:](https://openjfx.io/) Version 20
- [Jsoup:](https://jsoup.org/) Library for HTML parsing and data extraction
- [Atlantafx-base:](https://mkpaz.github.io/atlantafx/) Stylish themes for JavaFX
- [Preferences API:](https://docs.oracle.com/javase/8/docs/api/java/util/prefs/package-summary.html) For managing
  persistent user settings

## Acknowledgment & Data Source

This app uses data from [Numbeo](https://www.numbeo.com/common/), the world's largest database of user-contributed data
about global prices and
statistics. We thank Numbeo for providing access to their data under their terms of use.

The data is used solely for personal, non-commercial purposes. All rights to the data belong to Numbeo. For more
information on global prices and statistics, visit [Numbeo](https://www.numbeo.com/cost-of-living/).

## Wiki

For in-depth explanations about each part of the project, visit
our [Wiki](https://github.com/bruch-alex/Global-Goods-Index/wiki).
The Wiki provides detailed documentation about the application’s structure, features, and functionality.

## Project Developer Team

- [Alex Bruch](https://github.com/bruch-alex)
- [Natalie Lazarev](https://github.com/nat-laz)

## How to Build & Run

### How to Build executable file?

1. Download this repo
2. Open terminal in a root folder and run this command

```
mvn clean package
```

This command will create `/taget` folder and executable `sav-version-jar-with-dependencies.jar` inside where `version`
will look like `1.4` for example.

