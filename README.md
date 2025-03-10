```markdown
# BlogApp Mobile Application

**BlogApp** is a mobile application built in Java that allows users to browse, create, and interact with blog posts. It connects to a Django backend via a REST API, enabling seamless data synchronization and user authentication. Perfect for bloggers who want a clean, intuitive mobile experience!

## 🚀 Getting Started

Follow these steps to set up and run the BlogApp on your local machine.

### Prerequisites

- **Backend Setup**: Ensure the [Django Blog API](https://github.com/your-django-backend-repo) is set up and running.  
- **Android Studio**: Install Android Studio (latest version) for building the mobile app.  
- **Java Development Kit (JDK)**: Version 11 or higher.  
- **API Base URL**: Update the mobile app with your Django backend URL (see [Configuration](#configuration)).

---

## Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/blogapp-mobile.git
cd blogapp-mobile
```

### 2. Backend Preparation
1. Clone and run the [Django Blog API](https://github.com/Jamesnavigator2001/blogApp-backend).  
2. Install dependencies:
   ```bash
   pip install -r requirements.txt
   ```
3. Run migrations and start the server:
   ```bash
   python manage.py migrate
   python manage.py runserver
   ```
   
### 3. Mobile App Configuration
- Open the project in **Android Studio**.  
- Navigate to `app/src/main/java/com/example/blogapp/constants/ApiConfig.java`.  
- Replace `BASE_URL` with your Django backend URL (e.g., `http://10.0.2.2:8000` for local emulator).

### 4. Build and Run
- Sync the project with Gradle.  
- Connect an Android device/emulator.  
- Click **Run** in Android Studio to install and launch the app.

---

## Configuration

### Environment Variables (Backend)
Add these to your Django `.env` file:
```python
DEBUG=True
SECRET_KEY=your-secret-key
ALLOWED_HOSTS=localhost, 127.0.0.1
```

### CORS Setup (Backend)
Ensure your Django backend allows requests from the mobile app by configuring `CORS_ALLOWED_ORIGINS` in `settings.py`:
```python
CORS_ALLOWED_ORIGINS = [
    "http://localhost:8000",
    "http://10.0.2.2:8000",  # Android emulator
]
```

---

## 🤝 Contributing
Contributions are welcome! Fork the repository, create a feature branch, and submit a pull request.  
Report bugs or suggestions via [https://github.com/Jamesnavigator2001/blogaApp-mobileApp/issues/](https://github.com/Jamesnavigator2001/blogaApp-mobileApp/issues/).

---

## 📧 Contact
For questions or support, reach out to:  
- **Email**: [jamesnavigator04@gmail.com](jamesnavigator04@gmail.com)  
- **GitHub**: [https://github.com/Jamesnavigator2001/](https://github.com/Jamesnavigator2001/)

---

**Happy Blogging!** 📱✨
``` 
