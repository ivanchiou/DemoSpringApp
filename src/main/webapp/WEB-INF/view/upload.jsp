<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>File Upload</title>
</head>
<body>
    <h1>Upload a File</h1>
    <form method="post" action="/upload" enctype="multipart/form-data">
        <label for="file">Choose a file:</label>
        <input type="file" id="file" name="file" required>
        <br><br>
        <button type="submit">Upload</button>
    </form>
</body>
</html>
