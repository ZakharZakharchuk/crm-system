# Запити зацікавлених осіб

## Вступ

Цей розділ містить інформацію для осіб, які зацікавлені використанні системи **GymCRM**. Тут задокументовано інформацію про цільову аудиторію продукту, різні бізнес сценарії використання а також вимоги до системи.

### Мета
Створення документу запитів зацікавлених осіб для GTM CRM System полягає в тому, щоб провести аналіз цільової аудиторії продукту, виявити їхні вимоги та запити щодо функціональності, практичності, надійності, продуктивності та експлуатаційної придатності. Цей аналіз допоможе зрозуміти потреби та очікування користувачів, щоб розробити сучасне та зручне програмне забезпечення для керування проєктами, яке відповідатиме їхнім потребам та сприятиме ефективному використанню системи.

### Контекст
У цьому документі ретельно розглянуті всі можливі способи взаємодії з системою та вимоги, яким повинен відповідати готовий продукт.

## Основні визначення та скорочення

- **Мікросервісна архітектура**
   
   Мікросервісна архітектура - це архітектурний підхід до розробки програмного забезпечення, в якому програма розбивається на невеликі незалежні компоненти, які називаються мікросервісами. Кожен мікросервіс виконує окрему функцію та має власну базу коду, що дозволяє розробникам працювати над ними незалежно. Мікросервісна архітектура сприяє гнучкості, масштабованості та швидкості розробки та впровадження програмного забезпечення.

- **Автентифікація**
   
   Автентифікація - це процес перевірки ідентичності користувача або системи. У контексті програмного забезпечення цей процес включає перевірку введених облікових даних з даними, що зберігаються в системі, для підтвердження ідентичності користувача.

- **Representational State Transfer (REST)**
   
   Representational State Transfer (REST) - це архітектурний стиль для розробки веб-сервісів, який базується на принципах стандартних протоколів HTTP. У веб-сервісах REST ресурси (наприклад, дані або функції) ідентифікуються унікальними URI, а їх стан передається через представлення (наприклад, JSON або XML) за допомогою стандартних HTTP-методів, таких як GET, POST, PUT та DELETE.

- **bcrypt**
   
   bcrypt - це алгоритм хешування паролів, який забезпечує високий рівень захисту паролів користувачів шляхом додаткових операцій з сіллю та ітераціями. Використовується для безпечного зберігання паролів у базі даних.

## Короткий огляд продукту

Система управління фітнес-клубом - це комплексний набір інструментів і функцій, призначений для забезпечення ефективного управління фітнес-проектами та допомагає досягти їхніх цілей в рамках обмежень, таких як час, ресурси та бюджет.

GymCRM - це програмне забезпечення для управління фітнес-проектами, яке спрощує створення та організацію різних фітнес-ініціатив. Завдяки своїм функціям, воно допомагає ефективно керувати процесом та контролювати його.

Основні категорії користувачів системи:

Тренери фітнес-клубу - спеціалісти, які виконують конкретні завдання в рамках проекту. Вони використовують систему для відстеження своїх завдань та спілкування з іншими учасниками.
Клієнти(відвідувачі) фітнес-клубу - особи, які користуються послугами фітнес-клубу та можуть мати власні активності та тренування.

## Функціональність

Система GymCRM надає різні функціональні можливості для користувачів з різними ролями та доступом. Це дозволяє кожній групі користувачів виконувати свої завдання та керувати процесами в спортивному залі.

### Інтерфейс тренера

Інтерфейс тренера надає базовий набір функцій для взаємодії з системою.

- Реєстрація користувача
- Авторизація користувача
- Редагування профілю користувача в системі
- Перегляд списку клієнтів
- Запланування тренувань для клієнтів
- Перегляд та аналіз статистики тренувань

### Інтерфейс клієнта

Клієнти можуть користуватися інтерфейсом для зручного спілкування з тренером та відстеження власного прогресу.

- Реєстрація в системі
- Авторизація в системі
- Редагування власного профілю
- Перегляд графіка тренувань
- Запис на тренування
- Перегляд статистики власного прогресу

## Практичність

GymCRM прагне забезпечити зручний та ефективний інтерфейс для користувачів усіх рівнів навичок.

- Мінімалістичний та інтуїтивно зрозумілий дизайн
- Докладна та доступна документація
- Підтримка гарячих клавіш
- Можливість створення бекапів та відновлення даних
- Адаптивний дизайн для різних типів пристроїв та екранів
- Кросплатформенність

## Надійність

GymCRM забезпечує надійний та безпечний обмін інформації та доступ до даних завдяки таким функціям:

- **Авторизація користувачів:** Користувачі мають доступ лише до тих функцій, на які вони мають права.
- **Чітке розділення можливостей користувачів:** Користувачі мають доступ тільки до функцій, необхідних для їхньої ролі в проєкті.
- **Шифрування даних:** Забезпечується безпека конфіденційних даних під час їх передачі та зберігання. Шифрування за допомогою bcrypt.
- **Система повідомлень про проблеми:** Користувачі отримують повідомлення про будь-які проблеми або несправності в системі.
- **Регулярні резервні копії:** Здійснюється регулярне створення резервних копій даних для уникнення втрат.
- **Стійкість до високого трафіку:** Система здатна витримувати великий потік користувачів та запитів без втрати продуктивності.
- **Захищене з'єднання між мікросервісами** Система використовує стандарт HTTPS. А також API Gateway як вхідну точку з Інтернету, а сервіси мають суворі правила на вхідний/вихідний трафік.

## Продуктивність

GymCRM прагне забезпечити ефективність та швидкість роботи завдяки наступним функціям:

- **Швидкий обмін даними:** Забезпечується швидкий та ефективний обмін інформацією між користувачами та сервером.
- **Ефективне використання ресурсів:** Мінімізується використання оперативної пам'яті та інтернет-трафіку.
- **Використання облачних сервісів:** Використовуються облачні технології для забезпечення надійності та доступності даних.
- **Висока пропускна здатність:** Забезпечується висока швидкість обробки запитів та відповідей.
- **Масштабування** Використання мікросервісної архітектури забезпечує легке горизонтальне масштабування.

## Сценарії використання
Для оптимальної взаємодії було виділено наступні сценарії:
- [Trainee Registration](#TraineeRegistration)
- [Trainer Registration](#TrainerRegistration)
- [Login](#Login)
- [Get Trainee Profile](#GetTraineeProfile)
- [Update Trainee Profile](#UpdateTraineeProfile)
- [Delete Trainee Profile](#DeleteTraineeProfile)
- [Get Trainer Profile](#GetTrainerProfile)
- [Update Trainer Profile](#UpdateTrainerProfile)
- [Delete Trainer Profile](#DeleteTrainerProfile)
- [Add Training](#AddTraining)
- [Get Trainer Trainings List](#GetTrainerTrainingsList)
- [Get Trainee Trainings List](#GetTrainee-TrainingsList)
- [Get Trainer Trainings History](#GetTrainerTrainingsHistory)

| ID               | <span id=TraineeRegistration>`Trainee Registration`</span>                                                                                                                                                                           |
| :--------------- |:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Назва:           | Реєстрація учасника                                                                                                                                                                                                                  |
| Учасники:        | Користувач(відвідувач), система                                                                                                                                                                                                                   |
| Передумови:      | - Користувач відкриває сторінку реєстрації учасника                                                                                                                                                                                 |
| Результат:       | Створений обліковий запис учасника                                                                                                                                                                                                   |
| Виключні ситуації: | Користувач не вказав обов'язкові поля (перевірка на сервері) <br> Користувач вказав некоректні дані (перевірка на сервері) <br> Системна помилка при обробці запиту                                                     |
| Основний сценарій: | 1. Користувач відкриває сторінку реєстрації учасника <br> 2. Користувач вводить обов'язкові дані (Ім'я, Прізвище) та необов'язкові дані (Дата народження, Адреса) <br> 3. Користувач натискає кнопку "Відправити" <br> 4. Система створює обліковий запис учасника |

| ID               | <span id=TrainerRegistration>`Trainer Registration`</span>                                                                                                                                                                           |
| :--------------- |:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Назва:           | Реєстрація тренера                                                                                                                                                                                                                    |
| Учасники:        | Користувач(тренер), система                                                                                                                                                                                                                   |
| Передумови:      | - Користувач відкриває сторінку реєстрації тренера                                                                                                                                                                                 |
| Результат:       | Створений обліковий запис тренера                                                                                                                                                                                                    |
| Виключні ситуації: | Користувач не вказав обов'язкові поля (перевірка на сервері) <br> Користувач вказав некоректні дані (перевірка на сервері) <br> Системна помилка при обробці запиту                                                     |
| Основний сценарій: | 1. Користувач відкриває сторінку реєстрації тренера <br> 2. Користувач вводить обов'язкові дані (Ім'я, Прізвище, Спеціалізація) <br> 3. Користувач натискає кнопку "Відправити" <br> 4. Система створює обліковий запис тренера |

| ID               | <span id=Login>`Login`</span>                                                                                                                                                                                                         |
| :--------------- |:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Назва:           | Авторизація                                                                                                                                                                                                                            |
| Учасники:        | Користувач(відвідувач, тренер), система                                                                                                                                                                                                                   |
| Передумови:      | - Користувач відкриває сторінку входу                                                                                                                                                                                                 |
| Результат:       | Успішна авторизація користувача                                                                                                                                                                                                      |
| Виключні ситуації: | Користувач ввів невірний логін або пароль (перевірка на сервері) <br> Системна помилка при обробці запиту                                                                                                                           |
| Основний сценарій: | 1. Користувач відкриває сторінку входу <br> 2. Користувач вводить свій логін та пароль <br> 3. Користувач натискає кнопку "Увійти" <br> 4. Система перевіряє логін та пароль користувача <br> 5. У разі успішної перевірки система надає доступ користувачеві |
| 6. Система перенаправляє користувача на основну сторінку |

| ID               | <span id=GetTraineeProfile>`Get Trainee Profile`</span>                                                                                                                                                                               |
| :--------------- |:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Назва:           | Отримання профілю учасника                                                                                                                                                                                                             |
| Учасники:        | Користувач(відвідувач), система                                                                                                                                                                                                                    |
| Передумови:      | - Користувач увійшов у систему та відкриває свій профіль                                                                                                                                                                             |
| Результат:       | Відображення профілю учасника                                                                                                                                                                                                          |
| Виключні ситуації: | Системна помилка при обробці запиту                                                                                                                                                                                                    |
| Основний сценарій: | 1. Користувач увійшов у систему та відкриває свій профіль <br> 2. Система відображає інформацію про учасника                                                                                                                                 |

| ID               | <span id=UpdateTraineeProfile>`Update Trainee Profile`</span>                                                                                                                                                                         |
| :--------------- |:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Назва:           | Оновлення профілю учасника                                                                                                                                                                                                             |
| Учасники:        | Користувач(відвідувач), система                                                                                                                                                                                                                    |
| Передумови:      | - Користувач увійшов у систему та відкриває свій профіль для редагування                                                                                                                                                             |
| Результат:       | Оновлена інформація профілю учасника                                                                                                                                                                                                  |
| Виключні ситуації: | Користувач ввів некоректні дані (перевірка на сервері) <br> Системна помилка при обробці запиту                                                                                                                                      |
| Основний сценарій: | 1. Користувач увійшов у систему та відкриває свій профіль для редагування <br> 2. Користувач вносить необхідні зміни в свій профіль (Ім'я, Прізвище, Дата народження, Адреса) <br> 3. Користувач натискає кнопку "Оновити" <br> 4. Система переві

| ID               | <span id=DeleteTraineeProfile>`Delete Trainee Profile`</span>                                                                                                                                                                  |
| :--------------- |:-----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Назва:           | Видалення профілю учасника                                                                                                                                       |
| Учасники:        | Користувач(відвідувач), система                                                                                                                                              |
| Передумови:      | - Користувач увійшов у систему та відкриває профіль учасника для видалення                                                                                     |
| Результат:       | Видалення профілю учасника з системи                                                                                                                             |
| Виключні ситуації: | Системна помилка при обробці запиту                                                                                                                             |
| Основний сценарій: | 1. Користувач увійшов у систему та відкриває профіль учасника для видалення <br> 2. Користувач натискає кнопку "Видалити" <br> 3. Система підтверджує намір видалення користувача <br> 4. Система видаляє профіль учасника з системи |

| ID               | <span id=GetTrainerProfile>`Get Trainer Profile`</span>                                                                                                                                                                        |
| :--------------- |:-----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Назва:           | Отримання профілю тренера                                                                                                                                        |
| Учасники:        | Користувач(тренер), система                                                                                                                                              |
| Передумови:      | - Користувач увійшов у систему та відкриває профіль тренера                                                                                                    |
| Результат:       | Відображення профілю тренера                                                                                                                                     |
| Виключні ситуації: | Системна помилка при обробці запиту                                                                                                                             |
| Основний сценарій: | 1. Користувач увійшов у систему та відкриває профіль тренера <br> 2. Система відображає інформацію про тренера                                             |

| ID               | <span id=UpdateTrainerProfile>`Update Trainer Profile`</span>                                                                                                                                                                  |
| :--------------- |:-----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Назва:           | Оновлення профілю тренера                                                                                                                                         |
| Учасники:        | Користувач(тренер), система                                                                                                                                              |
| Передумови:      | - Користувач увійшов у систему та відкриває профіль тренера для редагування                                                                                   |
| Результат:       | Оновлена інформація профілю тренера                                                                                                                              |
| Виключні ситуації: | Користувач ввів некоректні дані (перевірка на сервері) <br> Системна помилка при обробці запиту                                                              |
| Основний сценарій: | 1. Користувач увійшов у систему та відкриває профіль тренера для редагування <br> 2. Користувач вносить необхідні зміни в профіль тренера (Ім'я, Прізвище, Спеціалізація, Активний) <br> 3. Користувач натискає кнопку "Оновити" <br> 4. Система перевіряє введені дані та оновлює профіль тренера |
  
| ID               | <span id=DeleteTrainerProfile>`Delete Trainer Profile`</span>                                                                                                                                                                  |
| :--------------- |:-----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Назва:           | Видалення профілю тренера                                                                                                                                        |
| Учасники:        | Користувач(тренер), система                                                                                                                                              |
| Передумови:      | - Користувач увійшов у систему та відкриває профіль тренера для видалення                                                                                     |
| Результат:       | Видалення профілю тренера з системи                                                                                                                              |
| Виключні ситуації: | Системна помилка при обробці запиту                                                                                                                             |
| Основний сценарій: | 1. Користувач увійшов у систему та відкриває профіль тренера для видалення <br> 2. Користувач натискає кнопку "Видалити" <br> 3. Система підтверджує намір видалення тренера <br> 4. Система видаляє профіль тренера з системи   |

| ID                 | <span id=AddTraining>`Add Training`</span>                                                                                                                     |
| :----------------- |:--------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Назва:             | Додавання тренування                                                                                                                                         |
| Учасники:          | Користувач(тренер), система                                                                                                                                          |
| Передумови:        | - Користувач увійшов у систему та відкриває сторінку додавання тренування                                                                                    |
| Результат:         | Додане тренування                                                                                                                                             |
| Виключні ситуації: | Користувач не вказав всі обов'язкові дані (перевірка на сервері) <br> Системна помилка при обробці запиту                                                     |
| Основний сценарій: | 1. Користувач увійшов у систему та відкриває сторінку додавання тренування <br> 2. Користувач вводить всі необхідні дані (ім'я учасника, ім'я тренера, дата тренування, тип тренування, тривалість) <br> 3. Користувач натискає кнопку "Додати тренування" |

| ID                 | <span id=GetTrainerTrainingsList>`Get Trainer Trainings List`</span>                                                                                                                         |
| :----------------- |:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Назва:             | Отримання списку тренувань тренера                                                                                                                                                           |
| Учасники:          | Користувач(тренер), система                                                                                                                                                                         |
| Передумови:        | - Користувач увійшов у систему та відкриває сторінку отримання списку тренувань тренера                                                                                                    |
| Результат:         | Відображення списку тренувань тренера                                                                                                                                                       |
| Виключні ситуації: | Системна помилка при обробці запиту                                                                                                                                                         |
| Основний сценарій: | 1. Користувач увійшов у систему та відкриває сторінку отримання списку тренувань тренера <br> 2. Система виводить список тренувань, які були проведені тренером  |

| ID                 | <span id=GetTraineeTrainingsList>`Get Trainee Trainings List`</span>                                                                                                                         |
| :----------------- |:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Назва:             | Отримання списку тренувань учасника                                                                                                                                                         |
| Учасники:          | Користувач(відвідувач), система                                                                                                                                                                         |
| Передумови:        | - Користувач увійшов у систему та відкриває сторінку отримання списку тренувань учасника                                                                                                  |
| Результат:         | Відображення списку тренувань учасника                                                                                                                                                      |
| Виключні ситуації: | Системна помилка при обробці запиту                                                                                                                                                         |
| Основний сценарій: | 1. Користувач увійшов у систему та відкриває сторінку отримання списку тренувань учасника <br> 2. Система виводить список тренувань, що були здійснені учасником |

| ID                 | <span id=GetTrainerTrainingsHistory>`Get Trainer Trainings History`</span>                                                                                                         |
| :----------------- |:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Назва:             | Отримання історії тренувань тренера за період                                                                                                                                        |
| Учасники:          | Користувач(тренер), система                                                                                                                                                                 |
| Передумови:        | - Користувач увійшов у систему та відкриває сторінку отримання історії тренувань тренера за певний період                                                                           |
| Результат:         | Відображення історії тренувань тренера за обраний період                                                                                                                            |
| Виключні ситуації: | Системна помилка при обробці запиту                                                                                                                                                 |
| Основний сценарій: | 1. Користувач увійшов у систему та відкриває сторінку отримання історії тренувань тренера за певний період<br> 2. Користувач вказує період, за який потрібно отримати історію тренувань<br> 3. Користувач натискає кнопку "Пошук" <br> 4. Система виводить історію тренувань тренера за обраний період |
