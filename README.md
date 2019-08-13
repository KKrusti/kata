# Spaceboost code challenge

Welcome to the Spaceboost code challenge. We would like to have a restful API in order to manage the creation of our entities and obtain some statistics. 

The challenge consists in a Java SpringBoot Application.

## Introduction

You're given the following entities distributed in three JSON files located in the resources folder.

- Campaign
    - id

- AdGroup
    - id
    - campaignId
    - clicks
    - conversions
    - cost

- Keyword
    - id
    - campaignId
    - adGroupId
    - clicks
    - conversions
    - cost

## Rules

Consider the following:

1. One campaign can have N ad groups.
2. One ad group can have N keywords.
3. One campaign can have N keywords but at least one ad group.
4. One ad group belongs to a single campaign.
5. One keyword belongs to a single ad group.
6. One ad group can be created if and only if the campaign exists, otherwise the application must return HTTP status code 403.
7. One keyword can be created if and only if the ad group exists, otherwise the application must return HTTP status code 403.

## Goals

First, we need to be able to: 

* Create and get a single campaign.
* Create and get a single adGroup.
* Create and get a single keyword.

Secondly, we'll need to obtain the following metrics.

* The most clicked keyword.
* The keyword with most conversions.
* The campaign with most cost but less conversions.

Note: create an endpoint for each goal 

## Examples

We want to get the keyword with identifier 1. 
Assuming that it belongs to campaign id 2 and ad group id 3, then the endpoint would be:

```
GET /campaigns/2/adGroups/3/keywords/1
```

We want to create a keyword with identifier 2 that belongs to ad group 3 and campaign 2. 

```
POST /keywords
body: {"id":2,"campaignId":2,"adGroupId":3,"clicks":0,"conversions":0,"cost":0.00}
```

We want to obtain the most converted keyword.

```
GET keywords/getMostConverted
```

## Important

Project must compile and all classes must be tested.

Make sure your application runs with:

```
> $ mvn spring-boot:run
```

## What we will value
- Clean code
- Avoid bugs
- Good test coverage
- Object Orientation practices
- Adherence to SOLID principles
- Good performance
- Concurrent file management
- Data structures and algorithms used as well as their space and time complexity.
- Error handling


## Notes
- Take advantage of the provided default classes.
- You're not allowed to modify any of the provided entities JSON files (campaigns, adGroups, keywords).
- You're not allowed to use any kind of databases.

## How to submit
Create a zip file and send it to the email that had this project attached. Please make sure to include all your source
files and your git folders with it.
## 
