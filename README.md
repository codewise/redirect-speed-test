# Redirect speed test

This tool allows you to test speed of redirects from various locations using API of GTMetrix.com platform, and it works with both their free and PRO plans. It allows potential customers of Voluum to compare speed of their current tracking solution with Voluum by using trusted third-party solution.
It works by getting HAR ('waterfall' chart of requests) from the service, and analyzing it to see how long it takes for the first request to destination domain to appear.

Usage:
  - Adjust parameters in `RedirectSpeedTest` class:
    - `EMAIL` & `API_KEY` - obtained from GTMETRIX.com account
    - `URL` - URL to test
    - `LANDING_PAGE_DOMAIN` - any substring of target domain, eg. _google_, or _google.com_
  - Run the main method of `RedirectSpeedTest` class
  - Results will be saved in `report.csv` file
