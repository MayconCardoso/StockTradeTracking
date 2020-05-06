# Stock Trade Tracking
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg?style=for-the-badge)](https://ktlint.github.io/) 
[![kotlin](https://img.shields.io/github/languages/top/MayconCardoso/Mvvm-Architecture-Toolkit.svg?style=for-the-badge)](https://kotlinlang.org/) 
[![Android API](https://img.shields.io/badge/api-21%2B-brightgreen.svg?style=for-the-badge)](https://android-arsenal.com/api?level=21)
[![License Apache-2.0](https://img.shields.io/github/license/MayconCardoso/Mvvm-Architecture-Toolkit.svg?style=for-the-badge&color=yellow)](https://opensource.org/licenses/Apache-2.0) 

<img src=".github/stock-trade.png" 
alt="Stock Trade Logo" title="The logo himself" align="Right" />

## About

Stock Trade Tracking is an app where you can register your [Ibovespa](https://pt.wikipedia.org/wiki/Ibovespa) shares and tracking your profit or loss automatically (Hopefully your profit).

It is a simple showcase for a modern and well-crafted Android application, powered by [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines) and [Clean Architecture](https://www.amazon.com.br/Clean-Architecture-Craftsmans-Software-Structure/dp/0134494164). It is a real modularized MVVM application. 

<img src="./.github/gif1.gif" alt="with-internet" width="200px"/> <img src="./.github/gif2.gif" alt="withou-internet" width="200px"/> <img src="./.github/gif3.gif" alt="withou-internet" width="200px"/>

## Project Overview
The code is structured in a multi-module fashion, with semantics guided by Clean Architecture.

The domain is a horizontal module that defines all business rules and the core protocols to be implemented by low-level details, while aggregates them in high-level policies by using the [UseCase](http://www.plainionist.net/Implementing-Clean-Architecture-UseCases/) concept from Clean Architecture.

<p align="center">
  <img src=".github/android_studio_modules.png" 
alt="Stock Trade Logo" title="The logo himself" align="Center" />
</p>

Below you can see a better representation of how the project is structured in terms of modules dependencies. If you look at the diagram arrows you are going to see that the domain layer does not depend on any other module. It is a pure Kotlin module. 

![module-structure](.github/project_module_structure.png)

## Related Libs
Here are some personal libraries used to develop this app:

* [MVVM Archictecture Toolkit](https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit)
* [Architecture Boilerplate Generator](https://github.com/MayconCardoso/ArchitectureBoilerplateGenerator) 
* [Keyboard Visibility Monitor](https://github.com/MayconCardoso/KeyboardVisibilityMonitor)

## Roadmap

You can check out the [Project Board](https://github.com/MayconCardoso/StockTradeTracking/projects/1) to see the next steps.
