import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Realm {
    //Класс для чтения введенных строк из консоли
    private static BufferedReader br;
    //Игрок должен храниться на протяжении всей игры
    private static FantasyCharacter player = null;
    //Класс для битвы можно не создавать каждый раз, а переиспользовать
    private static BattleScene battleScene = null;
    private static FightCallback fightCallback;

    /*public class FightCallback {
        public static void fightLost() {
            System.out.printf("%s проиграл!%n", Realm.player.getName());
        }

        public static void fightWin() {
            System.out.printf("%s победил!%n", Realm.player.getName());
        }
    }
*/
    public static void main(String[] args) {
        //Инициализируем BufferedReader
        br = new BufferedReader(new InputStreamReader(System.in));
        //Инициализируем класс для боя
        battleScene = new BattleScene();
        //Первое, что нужно сделать при запуске игры, это создать персонажа, поэтому мы предлагаем ввести его имя
        System.out.println("Введите имя персонажа:");
        //Далее ждем ввод от пользователя
        try {
            command(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void command(String string) throws IOException {
        //Если это первый запуск, то мы должны создать игрока, именем будет служить первая введенная строка из консоли
        if (player == null) {
            player = new Hero(
                    string,
                    100,
                    20,
                    20,
                    0,
                    0
            );
            System.out.printf("Спасти наш мир от драконов вызвался %s! Да будет его броня крепка и бицепс кругл!%n", player.getName());
            //Метод для вывода меню
            printNavigation();
        }
        //Варианты для команд
        switch (string) {
            case "1": {
                System.out.println("Торговец еще не приехал");
                command(br.readLine());
            }
            break;
            case "2": {
                commitFight();
            }
            break;
            case "3":
                System.exit(1);
                break;
            case "да":
                command("2");
                break;
            case "нет": {
                printNavigation();
                command(br.readLine());
            }
        }
        //Снова ждем команды от пользователя
        command(br.readLine());
    }
    private static void commitFight() {
        battleScene.fight(player, createMonster(), new FightCallback() {
            @Override
            public void fightWin() {
                System.out.println(String.format("%s победил! Теперь у вас %d опыта и %d золота, а также осталось %d едениц здоровья.", player.getName(), player.getXp(), player.getGold(), player.getHealthPoints()));
                System.out.println("Желаете продолжить поход или вернуться в город? (да/нет)");
                try {
                    command(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fightLost() {

            }
        });
    }

    private static void printNavigation() {
        System.out.println("Куда вы хотите пойти?");
        System.out.println("1. К Торговцу");
        System.out.println("2. В темный лес");
        System.out.println("3. Выход");
    }

    private static FantasyCharacter createMonster() {
        //Рандомайзер
        int random = (int) (Math.random() * 10);
        //С вероятностью 50% создается или скелет  или гоблин
        if (random % 2 == 0) return new Goblin(
                "Гоблин",
                50,
                10,
                10,
                100,
                20
        );
        else return new Skeleton(
                "Скелет",
                25,
                20,
                20,
                100,
                10
        );
    }

   public interface FightCallback {
        void fightWin();
        void fightLost();
    }
}

    /*private static void commitFight() {

        System.out.println("Я зашел в темный лес");
        Random random = new Random();
        FantasyCharacter monster;
        if (random.nextInt(100) > 50) {
            monster = new Goblin("Я гоблин",
                    100,
                    5,
                    5,
                    10,
                    50);
        } else {
            monster = new Skeleton("Я скелет",
                    100,
                    5,
                    5,
                    10,
                    50);
        }
        battleScene.fight(player, monster, fightCallback);
    }

    private static void printNavigation() {
        System.out.println("1. К торговцу");
        System.out.println("2. В темный лес");
        System.out.println("3. Назад");
    }
}
*/