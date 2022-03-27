# virtual-trading Service

## Description 

The idea is to create a dummy trading platform which can be used for learning stock marketing.
Buy, sell, portfolio, funds and live stock prices are the key features.
This service is aimed to be backend for the mobile app of "Apna Trading" platform.

## Key features 

1. Login/signup
2. See latest stock prices
3. Watchlist
4. Buy
5. Sell
6. Portfolio
7. User profile
8. Funds
9. Help

## Getting started

## Pre-requisite / Tech stack 

1. Docker
2. PSQL
3. Kotlin + Spring boot + Gradle + Kotlin DSL
4. Tmux
5. JDK11
6. Flyway

## Running service in local

- `./run.sh`
- Service will be accessible at - http://localhost:3030/virtual-trading/health/status

Common issues - 

- Sometimes docker full can fail for psql for multiple reasons
- Make sure pull happens successfully 

- Main screen can get error if docker pull fails
- Re-run the bootRun command again if it fails on the main screen 

## Run tests

- `./gradlew test`

## Run lint

- Install `ktlint` https://ktlint.github.io/
- Run `ktlint`

## Fix lint

- Install `ktlint` https://ktlint.github.io/
- Run `ktlint -F "src/**/*.kt"`
- There may some issues which needs manual fix, do them.

## Structure

Project has basic spring boot structure,

## Contribution Guidelines 

- Try to follow TDD while developing or fixing any feature
- Make sure to have sufficient code coverage while writing test cases, focus on scenarios' coverage will automatically take care.
- Report any issues

Copyright (c) 2022 Apna Trading.
