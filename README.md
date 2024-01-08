***
# Компьютерная графика
## _4 таск_
***
## Важно! В tag without_rasterization находится проект до добавления растеризации, заполнения полигонов цветом. 
### Это было сделано из-за недостаточной оптимизации растеризации, заполнения полигонов цветом. Преподаватель по практике разрешил залить такую версию, так как алгоритм верный, но мы решили иметь две версии проекта - до и после. 
***

### Первый человек (Анна Доброва): 
* Выполнена возможность загрузки, чтения, сохранения моделей
* Реализована загрузка нескольких моделей и выбор активной модели для работы
* Добавлена возможность удаления вершин и полигонов модели
* Сделан понятный интерфейс
* Обработка ошибок выполнена, но не всех
* Деплой в процессе
### Второй человек (Валерия Скобцова): 
* Создан модуль для математики
* Сделана работа с векторами-столбцами
* Добавлены аффинные преобразования
* Реализована трансформация модели и её сохранение после преобразований
* Выполнено управление камерой с помощью мыши
### Третий человек (Елена Малыхина): 
* Добавлена возможность триангуляции модели и вычисления ее нормалей после загрузки
* Выполнено заполнение полигонов одним цветом, используя растеризацию треугольников (в теге Rasterization2/ветке Rasterization5 (Не была добавлена в основной код, так как могла обрабатывать только простые модельки (куб), а сложнее уже с уменьшением фпс. Но практик сказал оптимизация не обязательна , и что код рабочий, просто медленный))
* Сделана поддержка нескольких камер, их добавление, смена, удаление
* Текстура, освещение, режимы отрисовки нужно сделать

[]()