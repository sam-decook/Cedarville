#include "SubGUIClass.h"

SubGUIClass::SubGUIClass( wxWindow* parent )
:
GUIClass( parent )
{
    statusBar->SetStatusText("X's turn", 0);
}

void SubGUIClass::OnExitSelected( wxCommandEvent& event ) {
    int answer = wxMessageBox("Quit program?", "Exit Confirmation",
        wxYES_NO, this);
    if (answer == wxYES) {
        this->Close();
    }
}

void SubGUIClass::OnTTTButtonClick( wxCommandEvent& event ) {
    wxObject* p = event.GetEventObject();
    wxButton* b = (wxButton *) p;
    char player;

    if (b->GetLabel() != 'X' && b->GetLabel() != 'O') { //if square is open
        if (xTurn) {
            player = 'X';
            xTurn = false;
            statusBar->SetStatusText("O's turn", 0);
        }
        else {
            player = 'O';
            xTurn = true;
            statusBar->SetStatusText("X's turn", 0);
        }

        b->SetLabel(player);
        CheckWin(player);

        if (gameWon) {
            if (player == 'X') {
                int answer = wxMessageBox("Game Over! Player X won!", "Play Again?",
                    wxYES_NO, this);
                if (answer != wxYES) {
                    this->Close();
                }
            }
            else {
                int answer = wxMessageBox("Game Over! Player O won!", "Play Again?",
                    wxYES_NO, this);
                if (answer != wxYES) {
                    this->Close();
                }
            }

            ResetGame();
        }
        else if (turn == 9) {
            int answer = wxMessageBox("Game Over! It's a tie", "Play Again?",
                wxYES_NO, this);
            if (answer != wxYES) {
                this->Close();
            }

            ResetGame();
        }
        else {
            turn++;
        }
    }
}

void SubGUIClass::OnResetButtonClick(wxCommandEvent& event) {
    ResetGame();
}

void SubGUIClass::OnExitButtonClick( wxCommandEvent& event ) {
    OnExitSelected(event);
}

void SubGUIClass::ResetGame() {
    gameWon = false;
    xTurn = true;
    turn = 1;

    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            gameArray[i][j]->SetLabel("");
        }
    }

    statusBar->SetStatusText("X's turn", 0);
}

void SubGUIClass::CheckWin(char player) {
    if ((MM->GetLabel() == player && TM->GetLabel() == player && BM->GetLabel() == player) ||  //middle vertical
        (MM->GetLabel() == player && ML->GetLabel() == player && MR->GetLabel() == player) ||  //middle horizontal
        (MM->GetLabel() == player && TL->GetLabel() == player && BR->GetLabel() == player) ||  //slant down
        (MM->GetLabel() == player && TR->GetLabel() == player && BL->GetLabel() == player) ||  //slant up
        (TL->GetLabel() == player && ML->GetLabel() == player && BL->GetLabel() == player) ||  //left vertical
        (TL->GetLabel() == player && TM->GetLabel() == player && TR->GetLabel() == player) ||  //top horizontal
        (BR->GetLabel() == player && BL->GetLabel() == player && BM->GetLabel() == player) ||  //right vertical
        (BR->GetLabel() == player && TR->GetLabel() == player && MR->GetLabel() == player))    //bottom horizontal
    {
        gameWon = true;
    }
}
