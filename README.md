# Running the Project

## Requirements

- **Java JDK 17+** installed and configured in your system PATH.  
- **Maven** installed and configured in your system PATH to build and run the backend.  
- **Node.js (v16+)** and **npm** installed and configured in your system PATH to run the frontend.  
- **MySQL Server** installed and running locally (or accessible remotely).  
- A MySQL user with permissions to create databases and tables, or a pre-existing database configured.

## Setup and Run

1. **Clone the repository:**

   ```bash
   git clone <repository-url>
   cd <repository-folder>
    ```
2. **Prepare MySQL database**
   Before running the application, make sure to have a MySQL database created for the project. You can create the database manually or let the setup script handle it.

3. **Run the provided setup and run script:**
    There is a shell script run.sh which will:

    - Ask for your MySQL credentials and desired database name (default is notes_db).

    - Check if the database exists; if not, it will create it.

    - Start the backend server using Maven.

    - Install frontend dependencies and start the frontend server.

    To run the script, execute in your terminal:

    ```bash
   ./run.sh
    ```

4. **Access the application:**

    - The backend will start on http://localhost:8080.

    - The frontend will start on http://localhost:3000.
  

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

### Stopping the Application

To stop the aplication press ctrl+c in the terminal running the script.