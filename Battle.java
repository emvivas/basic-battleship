/**
* @program BasicBattleShip
* @author  Emiliano Vivas Rodríguez
* @email  a01424732@tec.mx
* @version 1.0
* @since   2021-03-13
*/

import java.util.Scanner;
import java.awt.Toolkit;
public final class Battle {
    private static char[][] map = new char[5][5];
    private static Player[] players = {new Player(), new Player()};
    public static void main(String[] args) throws InterruptedException {
        boolean player = false;
        byte[] location = new byte[2];
        final Scanner SCANNER = new Scanner(System.in).useDelimiter("\n");
        organize();
        System.out.print("\n\n\n¡Batalla naval! Acaba con el rival...");
        Toolkit.getDefaultToolkit().beep();
        try {
            do{
                System.out.println("\n\n\n\n\nTurno del jugador "+(player?2:1)+".\nTiene "+(players[player?1:0].getNumberRemainingShips())+" navíos restantes en su ejército.\n\n\n");
                drawMap(player);
                System.out.print("\n\n¡Acaba con el enemigo! Hunde sus navíos.\n\nIntroduce una coordenada en X ([1, 5]): ");
                location[0] = SCANNER.nextByte();
                System.out.print("Introduce una coordenada en Y ([1, 5]): ");
                location[1] = SCANNER.nextByte();
                switch (map[--location[0]][--location[1]]) {
                    case '0':
                        players[0].die();
                        if (player) System.out.println("\n¡Bien hecho, soldado! Acabaste con un barco de su armada.");
                        else System.out.println("\n¡Torpe, ¿por qué atacas a nuestros aliados?!");
                        break;
                    case '1':
                        players[1].die();
                        if (player) System.out.println("\n¡¿Te has vuelto loco?! ¡Destruiste un barco de nuestra flota!");
                        else System.out.println("\nLa nación está orgullosa de ti, hijo. Hundiste un navío del enemigo.");
                        break;
                    default:
                        System.out.println("\n¡Qué decepción! No hundiste ningún barco enemigo, soldado.");
                }
                player = !player;
                map[location[0]][location[1]] = 'F';
                location = new byte[]{players[0].getNumberRemainingShips(), players[1].getNumberRemainingShips()};
                Thread.sleep(5000);
            }while(location[0]>0 && location[1]>0);
        }catch (Exception e){
            System.out.println("\n\n\n\n\n¡Introduzca datos correctos para la próxima, hijo!");
            System.exit(0);
        } finally{ SCANNER.close(); }
        location[0] = (byte)(location[0] > location[1] ? 1 : 2);
        location[1] = (byte)Math.max(location[0], location[1]);
        System.out.println("\n\n\n\n\nEl ganador de esta feroz batalla es el jugador "+ location[0]+ " con "+ location[1]+ " navíos restantes.\n¡Enhorabuena, soldado!\n\n");
        drawMap();
        Toolkit.getDefaultToolkit().beep();
        Thread.sleep(7000);
    }
    private static void organize(){
        byte i, j;
        byte[] location;
        for(i=0; i<players.length;i++){
            for(j=0; j<players[i].SHIPS.length;j++){
                location = players[i].SHIPS[j].getLocation();
                if(map[location[0]][location[1]]!='\u0000'){
                    map = new char[5][5];
                    players = new Player[]{new Player(), new Player()};
                    i=0;j=-1;
                } else map[location[0]][location[1]] = Byte.toString(i).charAt(0);
            }
        }
        for (i = 0; i < map.length; i++) {
            for (j = 0; j < map[i].length; j++) {
                if (map[i][j] == '\u0000') map[i][j] = '*';
            }
        }
    }
    private static void drawMap(){
        for(char[] i:map){
            for(char j:i) System.out.printf("\t%c\t", j);
            System.out.println("\n");
        }
    }
    private static void drawMap(boolean player){
        for(byte i=0;i<map.length;i++){
            System.out.printf("\t%d\t\t",i+1);
            for(byte j=0;j<map[i].length;j++){
                if(map[i][j]=='0' && !player || map[i][j]=='1' && player) System.out.print("\tO\t");
                else if(map[i][j]!='F') System.out.print("\t[]\t");
                else System.out.print("\t"+map[i][j]+"\t");
            }
            System.out.println("\n");
        }
    }
    static class Player {
        private final Ship[] SHIPS = new Ship[5];
        private byte army = (byte)SHIPS.length;
        public Player(){
            for(byte i=0; i<5; i++) SHIPS[i] = new Ship();
        }
        public void die(){
            army--;
        }
        public byte getNumberRemainingShips(){
            return army;
        }
    }
    static class Ship{
        private final byte LOCATION_X, LOCATION_Y;
        public Ship(){
            LOCATION_X = (byte)Math.floor(Math.random() * 5);
            LOCATION_Y = (byte)Math.floor(Math.random() * 5);
        }
        public byte[] getLocation(){
            return new byte[]{LOCATION_X, LOCATION_Y};
        }
    }
}