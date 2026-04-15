# 🚀 Todo Management System with LINE Bot Integration

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![LINE API](https://img.shields.io/badge/LINE_Messaging_API-00C300?style=for-the-badge&logo=LINE&logoColor=white)

> **English:** A secure, RESTful Todo management API built with Spring Boot 3 and JWT authentication, seamlessly integrated with the LINE Messaging API. It features a cross-platform **Account Linking** mechanism, allowing users to manage their tasks directly via LINE without navigating to a web portal.
>
> **日本語:** Spring Boot 3 と JWT 認証を活用したセキュアなタスク管理システムです。LINE Messaging API と統合し、「アカウント連携（Account Link）」機能を実装することで、ユーザーは Web 画面にログインすることなく、日常的に使う LINE アプリから直接タスクの追加・確認が可能です。

---

## ✨ Key Features (主な機能と特徴)

### 🇺🇸 English
1. **JWT Authentication & Authorization:** Secured API endpoints using Spring Security. Users can only access their own resources.
2. **LINE Messaging API Integration:** A fully functional webhook controller receiving and processing user messages in real-time.
3. **Cross-Platform Account Linking:** Binds a user's unique `LINE User ID` to their database `Username`, enabling a passwordless, conversational UI experience for task management.
4. **Spring Boot 3 Compatibility:** Upgraded to support `jakarta` namespace and the latest LINE SDK v8.x architecture.

### 🇯🇵 日本語
1. **JWT 認証・認可:** Spring Security を用いたセキュアな API 設計。ユーザーは自身のデータのみにアクセス可能です。
2. **LINE Messaging API 統合:** Webhook を通じてユーザーからのメッセージをリアルタイムで受信・処理します。
3. **アカウント連携 (Account Linking):** ユーザーの `LINE User ID` とデータベース内のアカウント（Username）を紐付ける機能を実装。パスワード入力なしで LINE からタスク管理が可能です。
4. **Spring Boot 3 対応:** 最新の `jakarta` 仕様と LINE SDK v8.x アーキテクチャに完全対応しています。

---

## 🛠️ Tech Stack (技術スタック)
- **Backend:** Java (JDK 21), Spring Boot 3, Spring Security, Spring Data JPA
- **Database:** MySQL 8.0
- **Integration:** LINE Messaging API (line-bot-spring-boot-webmvc)
- **Tools:** Maven, ngrok, Postman

---

## 💬 LINE Bot Commands (Bot の使い方)
*(Scan your QR code to add the bot, then use the following commands)*

| Command (コマンド) | Description (説明) |
| :--- | :--- |
| `綁定帳號：[username]` | **Account Link:** Binds your LINE ID to your registered account.<br>(アカウント紐付け：LINE ID を登録済みアカウントと連携します) |
| `今日待辦` | **Read Tasks:** Retrieves and lists your pending tasks from the database.<br>(タスク確認：データベースから本日のタスク一覧を取得します) |
| `新增待辦：[Task]` | **Create Task:** Creates a new task and saves it to the database.<br>(タスク追加：新しいタスクを作成し、データベースに保存します) |

---

## 🔌 API Endpoints
| HTTP Method | Endpoint | Description | Requires JWT |
| :--- | :--- | :--- | :---: |
| **POST** | `/api/auth/register` | Register a new user | ❌ No |
| **POST** | `/api/auth/login` | User login (Returns Token) | ❌ No |
| **GET** | `/api/todos` | Get all todos for the current user | 🔒 Required |
| **POST** | `/api/todos` | Create a new todo | 🔒 Required |
| **PUT** | `/api/todos/{id}` | Update a specific todo | 🔒 Required |
| **DELETE** | `/api/todos/{id}` | Delete a specific todo | 🔒 Required |

---

## ⚙️ Getting Started (環境構築と実行)

### 1. Prerequisites
Ensure your development environment has the following installed:
* JDK 21 or higher
* Maven
* MySQL (Create an empty database named `todo_jwt_db`)
* [ngrok](https://ngrok.com/) (For local webhook tunneling)

### 2. Clone the repository
bash
git clone https://github.com/lil69samurai/todo-jwt.git
cd todo-jwt

### 3. Environment Settings
- Open src/main/resources/application.
- properties and modify the settings to match your database and LINE credentials:

(⚠️ Do not expose your real passwords or API tokens in public repositories!)

properties
### Database Settings
- spring.datasource.url=jdbc:mysql://localhost:3306/todo_jwt_db?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8
- spring.datasource.username=root
- spring.datasource.password=YOUR_MYSQL_PASSWORD
- spring.jpa.hibernate.ddl-auto=update

### LINE Bot Settings
- line.bot.channel-secret=YOUR_LINE_SECRET
- line.bot.channel-token=YOUR_LINE_TOKEN
- line.bot.handler.path=/callback
### 4. Build and Run (ビルドと実行)
- bash mvn clean install (Download dependencies and build the project)
- mvn spring-boot:run (Start the Spring Boot application)
### 5. Setup ngrok for LINE Webhook (Open a new terminal and start ngrok to expose your local server:

bash ngrok http 8080

Copy the https forwarding URL and paste it into your LINE Developers Console Webhook settings, appending /callback to the end (e.g., https://xxxx.ngrok.app/callback).

### 🚀 Future Improvements (今後の展望)
Integrate Swagger UI / SpringDoc for automatic API documentation generation.
Add JUnit and Mockito for comprehensive unit and integration testing.
Implement a Global Exception Handler to standardize error response formats.
text

---
