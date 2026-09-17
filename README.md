# DriveEase — Vehicle Rental Management System

So here's the deal: DriveEase is a Java app I built to help rental staff actually manage a fleet of vehicles without losing their minds. Think cars, bikes, SUVs — the whole lineup — plus customers, bookings, returns, and all the billing headaches that come with running a rental business. It's built around solid OOP principles, so under the hood it's clean and easy to extend.

## What it actually does

Here's what you can do with it:

- Add or remove vehicles — cars, bikes, SUVs, you name it
- Register new customers
- Check what's available to rent right now
- Book a vehicle for someone
- Handle returns
- Work out rental costs and slap on late fees when needed
- Spit out invoices
- Keep everything saved in a SQLite database
- Validate whatever data gets typed in, so garbage doesn't sneak through
- Run unit tests to make sure nothing's secretly broken

## Built with

- Java 17
- Maven
- SQLite
- JDBC
- JUnit 5
- Git and GitHub

## The OOP stuff going on under the hood

If you're curious about the design side of things, this project leans on:

- Encapsulation
- Inheritance
- Polymorphism
- Abstraction
- Interfaces and a service-based design
- Composition
- Proper exception handling

## Before you start

You'll need:

- Java JDK 17 or newer
- Maven 3.8 or newer
- Git

## Getting it running

First, grab the code:

```bash
git clone https://github.com/YOUR_USERNAME/driveease.git
cd driveease
```

Then compile it:

```bash
mvn clean compile
```

And fire it up:

```bash
mvn exec:java
```

Don't worry about the database — a `driveease.db` file gets created automatically the first time you run things.

## Running the tests

Want to make sure everything's working? Just run:

```bash
mvn test
```

## About the database

It's SQLite, nice and lightweight. You don't need to set up any tables yourself — the app takes care of that the first time it launches.

## How the project's organized

The whole thing is split into a few logical chunks:

- Vehicle Management
- Customer Management
- Rental Management
- Billing and Invoice Management
- Reporting and Rental Tracking

## What's next

There's a bunch of stuff I'd like to add down the road:

- A proper JavaFX interface instead of just the command line
- Admin login/authentication
- Online payment support
- Email notifications
- Better, more detailed revenue reports
- Scheduling for vehicle maintenance

---

Source: [github.com/tanay25bai10098-spec/driveease](https://github.com/tanay25bai10098-spec/driveease)
