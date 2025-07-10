## Endpoints
### Base path: /api/notes

POST /api/notes/create
Creat a new note.

GET /api/notes/{id}
Get note by id.

PUT /api/notes/{id}
Update note by id.

DELETE /api/notes/{id}
Delete note by id.

GET /api/notes/list
Get all notes.

GET /api/notes/active
Get all active notes.

GET /api/notes/archived
Get archived notes.

PUT /api/notes/{id}/archive
Archive note by id.

PUT /api/notes/{id}/unarchive
Unarchive note by id.

POST /api/notes/{id}/tags?tagName=
Link tag to a note(id) by its name.

DELETE /api/notes/{id}/tags?tagName=
Delete tag linked to a note(id) by its name.

GET /api/notes/by-tag?tagName=
Get all notes with certein tag by its name.

### Base path: /api/tags

POST /api/tags/create
Create a new tag.

GET /api/tags
Retrieve all tags.

GET /api/tags/{id}
Retrieve a tag by its ID.

PUT /api/tags/{id}
Update a tag's name by its ID.

DELETE /api/tags/{id}
Delete a tag by its ID.

GET /api/tags/{id}/notes
Retrieve all notes linked with a given tag ID.

