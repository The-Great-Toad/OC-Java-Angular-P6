# Frontend MDD - Angular Application

User interface for MDD social network (Monde de Dév).

## Tech Stack

- **Angular 20.3.0**
- **TypeScript 5.7**
- **RxJS 7.8**

## Prerequisites

- Node.js 18+
- npm 10+

## Installation

```bash
# 1. Install dependencies
npm install

# 2. Run development server
npm run start
```

Application available at `http://localhost:4200`

## Commands

```bash
npm start          # Start development server
npm run build      # Production build
npm test           # Unit tests
```

## Architecture

```
src/app/
├── core/              # Services, guards, interceptors, models
├── features/          # Feature modules
│   ├── auth/          # Authentication (login, register)
│   ├── feed/          # News feed
│   ├── topics/        # Topic list
│   ├── posts/         # Articles (detail, creation)
│   └── profile/       # User profile
├── layout/            # Header, navigation
└── shared/            # Reusable components
```

## Configuration

Backend API URL is defined in `src/environments/environment.ts`:

```typescript
apiUrl: 'http://localhost:8080/api';
```
