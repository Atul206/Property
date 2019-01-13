import csv
import json

csvfile = open('Bill_detail.csv', 'r')
jsonfile = open('file_0.json', 'w')
jsonfile.write('{\"sheet1\":[')
i = 1;
fieldnames = ("unique_property_id","zone","area_name","owenr_name", "gender", "relation_type", "relation_name" ,"mobile_no", "postal_add", "new_postal_add")
reader = csv.DictReader( csvfile, fieldnames)
for row in reader:
    if i%1000 == 0:
    	jsonfile.write(']}')
        filename = 'file_' + str(i) + '.json'
	print("\"" + filename + "\",")
	jsonfile = open(filename, 'w')
	jsonfile.write('{\"sheet1\":[')
	json.dump(row, jsonfile, indent=4)
    else:
	json.dump(row, jsonfile, indent=4)
    i = i + 1
    if i%1000 == 0:
     jsonfile.write('\n')
    else:
     jsonfile.write(',')
     jsonfile.write('\n')
	



