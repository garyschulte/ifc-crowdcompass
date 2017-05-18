require_relative 'ifc_date'
require 'date'

gdate1 = Date.new(2015,1,31)
gdate2 = Date.new(2015,12,31)
gdate3 = Date.new(2016,6,17)

idate1 = IFCDate.new(gdate1)
raise unless idate1.month == 2
raise unless idate1.day == 3

idate2 = IFCDate.new(gdate2)
raise unless idate2.month == 13
raise unless idate2.day == 29
raise unless idate2.year_day? == true

idate3 = IFCDate.new(gdate3)
raise unless idate3.month == 6
raise unless idate3.day == 29
raise unless idate3.leap_day? == true

puts "You made it!"