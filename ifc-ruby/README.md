# Background

The _International Fixed Calendar_ is a **totally real** calendar that has 13 months, each with 28 days. It synchronizes with the Gregorian calendar that most of us know and some of us love by sharing the same January 1st.

The months of the IFC are mostly the same as what you're used to:

1. January
2. February
3. March
4. April
5. May
6. June
7. Sol
8. July
9. August
10. September
11. October
12. November
13. December

# The Problem

We'd like to support the _International Fixed Calendar_ in Ruby. Please implement the following API:

```ruby
gregorian_date = Date.new(2015,1,31)
ifc_date = IFCDate.new(gregorian_date)

ifc_date.month
 #=> 2
ifc_date.day
 #=> 3
```

Because the year still needs to have 365 days, "Year Day" is always inserted as the 29th day of December:

```ruby
gregorian_date = Date.new(2015,12,31)
ifc_date = IFCDate.new(gregorian_date)

ifc_date.month
 #=> 13
ifc_date.day
 #=> 29
ifc_date.year_day?
 #=> true
```

Similarly, in leap years, "Leap Day" is inserted as the 29th day of June:

```ruby
gregorian_date = Date.new(2016,6,17)
ifc_date = IFCDate.new(gregorian_date)

ifc_date.month
 #=> 6
ifc_date.day
 #=> 29
ifc_date.leap_day?
 #=> true
```

# Instructions

Create a branch in this repository, but do not push it to a public remote. Do your work on that branch, committing with Git, and submit your final product to us by creating a zip file and emailing.

There is a file `run.rb`, which requires and uses an as-yet nonexistent `IFCDate` class in the manner shown by the examples above.

Run the file by executing `ruby run.rb`. It won't work at first! Without changing the contents of `run.rb`, make it execute to completion.

But that's not all! `run.rb` doesn't fully implement the _International Fixed Calendar_ as described above. Your implementation should!

Create any files outside of `run.rb` that you want to.

Feel free to work with any tools, techniques, and references that you like.

If you have any questions, please reach out to us.
