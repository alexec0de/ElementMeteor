# 🌟 ElementMeteor Plugin

[![Java](https://img.shields.io/badge/Java-8+-orange.svg)](https://java.com)
[![Paper](https://img.shields.io/badge/Paper-1.16.5-blue.svg)](https://papermc.io)
[![LiteCommands](https://img.shields.io/badge/LiteCommands-3.9.7-green.svg)](https://github.com/Rollczi/LiteCommands)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

> 🎯 **Тестовое задание для ElementCraft** - Плагин для майнкрафта с системой метеора

## 📋 Описание

ElementMeteor - это плагин для Paper 1.16.5, который добавляет способность **Метеор**.

### ⚡ Основные возможности

- **Команда `/elementmeteor`** - открывает GUI меню
- **Способность Метеор** - создает разрушительный метеор
- **Система прогрессии** - радиус взрыва увеличивается с опытом
- **База данных** - по заданию ТЗ
- **Анимированное восстановление** - блоки восстанавливаются с визуальными эффектами через 4 секунды


## 🚀 Быстрый старт

### Требования
- **Java 8+**
- **Paper 1.16.5**
- **ProjectKorra** 
- **MariaDB Сервер**

### Установка

1. **Скачайте JAR** из [Releases](../../releases)
2. **Поместите** в папку `plugins/`
3. **Настройте БД** в `config.yml`
4. **Перезапустите** сервер

### Конфигурация базы данных

```yaml
mariadb:
  host: "localhost"
  user: "root"
  password: "root"
  database: "ecMeteor"
  port: 3306
```

## 🎮 Использование

1. **Получите способность**: `/elementmeteor` → нажмите кнопку в меню
2. **Используйте метеор**: ЛКМ 
3. **Прогрессия**: Каждое использование увеличивает радиус взрыва

### Формула прогрессии
```
Радиус взрыва = (количество использований) × (базовый радиус)
```

## 📊 База данных

```sql
CREATE TABLE IF NOT EXISTS uses (
  name VARCHAR(36) PRIMARY KEY,
  uses INT NOT NULL DEFAULT 0
)
```

## 🔧 Разработка

### Сборка проекта

```bash
# Компиляция
./gradlew build

# Создание JAR с зависимостями
./gradlew shadowJar
```


## 📈 Особенности реализации

### ⚡ Асинхронная работа с БД
- Все операции I/O выполняются асинхронно
- HikariCP для оптимального пулинга соединений
- Graceful shutdown с корректным закрытием соединений

### 🔄 Интеграция с ProjectKorra
- Использование TempBlock API для временных блоков
- Корректная работа с системой способностей
- Соблюдение соглашений ProjectKorra

## 📝 Лицензия

Этот проект создан в рамках тестового задания для **ElementCraft**.

---


