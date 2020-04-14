package com.example.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.tictactoe.ComputerMove.findBestMove;

public class MainActivity extends AppCompatActivity {

    private Button[][] Board = new Button[3][3];

    //private boolean player = true;
    private int count= 0 , playerpoint = 0 , computerpoint = 0 ;

    private TextView textView1,textView2;

    private Button restartbtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pointshow();
        FillingBoard();

        restartbtn =(Button) findViewById(R.id.restart);
        restartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newBoard();
                playerpoint=0;
                computerpoint=0;
                pointshow();
            }
        });

    }


    private void FillingBoard(){

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                String id = "butt"+i+j;
                int resid = getResources().getIdentifier(id,"id",getPackageName());
                Board[i][j] = findViewById(resid);
                Board[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!((Button)v).getText().toString().equals(""))return;

                        if(count<9) {
                            ((Button) v).setText("O");
                            count++;
                            if(callforwinner()==1)chkplayer();
                            else if(count==9)draw();
                            //player = !player;
                        }
                        if(count<9){
                            computerAvailableCell();
                            count++;
                            if(callforwinner()==1)chkcomputer();
                            else if(count==9)draw();
                            //player = !player;
                        }

                    }
                });
            }
        }
    }


    private int callforwinner(){

        String [][] currentBoard = new String[3][3];

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                currentBoard[i][j] = Board[i][j].getText().toString();
            }
        }

        for(int i=0;i<3;i++){
            if(!currentBoard[i][0].isEmpty() && (currentBoard[i][0]==currentBoard[i][1]) && (currentBoard[i][2]==currentBoard[i][1]))return 1;
        }

        for(int i=0;i<3;i++){
            if(!currentBoard[0][i].isEmpty() && (currentBoard[0][i]== currentBoard[1][i]) && (currentBoard[1][i]==currentBoard[2][i])) return 1;
        }

        if(!currentBoard[1][1].isEmpty() && (currentBoard[1][1] == currentBoard[2][2]) && (currentBoard[0][0] == currentBoard[1][1]))return 1;

        if(!currentBoard[1][1].isEmpty() && (currentBoard[0][2] == currentBoard[2][0]) && (currentBoard[2][0] == currentBoard[1][1]))return 1;

        return 0;
    }

    private void chkplayer(){
        Toast.makeText(this,"player win",Toast.LENGTH_SHORT).show();
        playerpoint++;
        pointshow();
        newBoard();
    }

    private void chkcomputer(){
        Toast.makeText(this,"computer win",Toast.LENGTH_SHORT).show();
        computerpoint++;
        pointshow();
        newBoard();
    }

    private void draw(){
        Toast.makeText(this,"Draw",Toast.LENGTH_SHORT).show();
        newBoard();
    }

    private void pointshow(){
        textView1 = (TextView) findViewById(R.id.tv1);
        textView2 = (TextView) findViewById(R.id.tv2);
        textView1.setText("Player : " + playerpoint);
        textView2.setText("Computer : " + computerpoint);
    }

    private void newBoard(){

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                Board[i][j].setText("");
            }
        }
        //player = true;
        count = 0;
    }

    private void computerAvailableCell(){

        String [][] currentBoard1 = new String[3][3];

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                currentBoard1[i][j] = Board[i][j].getText().toString();
            }
        }
        //Here minmax algorithm call is done

        ComputerMove.Move move = findBestMove(currentBoard1);
        Board[move.row][move.col].setText("X");
    }

    // those method for rotating problem

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count",count);
        outState.putInt("playerpoint",playerpoint);
        outState.putInt("computerpoint",computerpoint);
        // outState.putBoolean("player",player);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        count = savedInstanceState.getInt("count");
        playerpoint = savedInstanceState.getInt("playerpoint");
        computerpoint = savedInstanceState.getInt("computerpoint");
        //player = savedInstanceState.getBoolean("player");
    }
}
