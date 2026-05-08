Ideas:
## Create .toString() methods for all classes
- allows for easy debugging and display of objects in the console

## Add a "Help" option to the menu
- Instead of showing everything at once, users can select "Help" to see a list of help options
    - "Keybinds" - shows all keybinds and their functions

## FileIO 
- read files to load user-made creations
- save user creations to files
- remove user creations
    - Opens menu which lists all user creations with corresponding numbers
        - user inputs number to select creation to remove
    - 
- custom file format (json below for simplicity)
- add to custom menu as 4th and 5th option
    - "Save"
    - "Load"
    - "Remove"

Example Load Menu:
```powershell
Load Creation:
1. Creation 1.json
2. Creation 2.json
3. Creation 3.json
Awaiting user input...
```powershell
Load Creation:
No Creations found in {cwd}
Awaiting user input...
```powershell


Example Save Menu:
```powershell
Save Creation:
Enter name for your creation (without extension):
Awaiting user input...
```
```powershell
Save Creation:
Invalid name. 
Awaiting user input...
```
Example Remove Menu:
```powershell
Remove Creation:
1. Creation 1.json
2. Creation 2.json
3. Creation 3.json
Enter number of creation to remove:
```
```powershell
Remove Creation:
No Creations found in {cwd}
Awaiting user input...
```