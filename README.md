# Itemist

## Description:
First bigger java, android app. 
Create notes, add subpoints, edit/delete, save data to file, read from file, few other features.

### What I would change:
- I would implements more classes, eg. Note class, Subpoint class.
- I would write code which is easier to test.

## Changelog 

### v.1.2.0

#### Features:
 - Moved AddSubpointButton  to the left corner of the activity.
 - In place of AddSubpointButton, now there is MoreButton (three pink dots align vertically). Depends on which activity you are, the button shows different items in dropList.  For every activity there is AboutItem. If you click on it, it moves you to AboutActivity. For NoteActivity, MoreButton shows another item - SettingsItem. On click moves you to SettingsActivity.
 - Added about activity. There is info about current version and email to me if you need help using the app or you have found an error. Clicking on the email is opening an email app. 
 - Added SettingsActivity. You can change the size of subpoints with slider.
 
#### Community proposal: 
 - Deleted counter from AddNoteActivity. There is no point of showing number of subpoints. It just spoils the look of activity.

### v.1.1.0

#### Features:
  - List of Notes is now moveable! You can move them by long click. They are saving where you have left them automatically.
  - New button on ChooseNoteActivity for deleting/editing (replacement of what was long click doing. Now long click moves objects.).
  
#### Bug Fixes:
  - Now ListView is working correctly with recycling items (no duplicates), adding/editing/deleting subnote now are not disabling checkboxes. 
  - Added few test classes.
  - When user deletes last note, now everything works correctly.
  - Checkboxes now not disabling when the view is scrolled down.

### v.1.0.1

#### Community proposal: 
  - Text of note/subpoint is added to the editing field while editing.

### v.1.0.0

#### Features:
  - Name note, add subpoints, save note. Delete notes, edit content.
