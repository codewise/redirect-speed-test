# Redirect speed test

This tool allows you to test speed of redirects from various locations using REST API
of [GTmetrix](https://gtmetrix.com/) platform.
All types of plans (free/paid/custom) can be used by customizing the configuration file.
The tool allows potential customers of Voluum to compare speed of their current tracking solution with Voluum by using trusted
third-party solution.
It works by retrieving data about redirection time directly from test results.
---
Usage:
1. Create configuration file named `configuration.yml` in `src/main/resources` directory:
   * copy the `configuration.yml.sample` file and remove `sample` from file extension
   * populate configuration fields according to hints present in the "sample file"
   * the `configuration.yml` file is ignored by default to prevent leaking api key
2. Run the main method of `RedirectSpeedTest` class.
3. By default, results will be saved in `results.csv` file.
    * results are saved in real time
4. Files for both input and output can be changed in `RedirectSpeedTest` class.
5. If you need more details about tests, logs can be found in `tests.log` file.
