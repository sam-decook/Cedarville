///////////////////////////////////////////////////////////////////////////
// C++ code generated with wxFormBuilder (version Jun 30 2011)
// http://www.wxformbuilder.org/
//
// PLEASE DO "NOT" EDIT THIS FILE!
///////////////////////////////////////////////////////////////////////////

#include "GUIClass.h"

///////////////////////////////////////////////////////////////////////////

GUIClass::GUIClass( wxWindow* parent, wxWindowID id, const wxString& title, const wxPoint& pos, const wxSize& size, long style ) : wxFrame( parent, id, title, pos, size, style )
{
	this->SetSizeHints( wxDefaultSize, wxDefaultSize );
	
	statusBar = this->CreateStatusBar( 1, wxST_SIZEGRIP, wxID_ANY );
	menubar = new wxMenuBar( 0 );
	menu = new wxMenu();
	wxMenuItem* menuItem;
	menuItem = new wxMenuItem( menu, wxID_ANY, wxString( wxT("Exit") ) + wxT('\t') + wxT("ALT-F4"), wxEmptyString, wxITEM_NORMAL );
	menu->Append( menuItem );
	
	menubar->Append( menu, wxT("File") ); 
	
	this->SetMenuBar( menubar );
	
	wxBoxSizer* bSizer1;
	bSizer1 = new wxBoxSizer( wxVERTICAL );
	
	m_panel1 = new wxPanel( this, wxID_ANY, wxDefaultPosition, wxDefaultSize, wxTAB_TRAVERSAL );
	wxBoxSizer* bSizer2;
	bSizer2 = new wxBoxSizer( wxVERTICAL );
	
	wxBoxSizer* bSizer3;
	bSizer3 = new wxBoxSizer( wxHORIZONTAL );
	
	TL = new wxButton( m_panel1, wxID_ANY, wxEmptyString, wxDefaultPosition, wxDefaultSize, 0 );
	bSizer3->Add( TL, 0, wxALL, 5 );
	
	TM = new wxButton( m_panel1, wxID_ANY, wxEmptyString, wxDefaultPosition, wxDefaultSize, 0 );
	bSizer3->Add( TM, 0, wxALL, 5 );
	
	TR = new wxButton( m_panel1, wxID_ANY, wxEmptyString, wxDefaultPosition, wxDefaultSize, 0 );
	bSizer3->Add( TR, 0, wxALL, 5 );
	
	bSizer2->Add( bSizer3, 0, wxALIGN_CENTER_HORIZONTAL, 5 );
	
	wxBoxSizer* bSizer31;
	bSizer31 = new wxBoxSizer( wxHORIZONTAL );
	
	ML = new wxButton( m_panel1, wxID_ANY, wxEmptyString, wxDefaultPosition, wxDefaultSize, 0 );
	bSizer31->Add( ML, 0, wxALL, 5 );
	
	MM = new wxButton( m_panel1, wxID_ANY, wxEmptyString, wxDefaultPosition, wxDefaultSize, 0 );
	bSizer31->Add( MM, 0, wxALL, 5 );
	
	MR = new wxButton( m_panel1, wxID_ANY, wxEmptyString, wxDefaultPosition, wxDefaultSize, 0 );
	bSizer31->Add( MR, 0, wxALL, 5 );
	
	bSizer2->Add( bSizer31, 0, wxALIGN_CENTER_HORIZONTAL, 5 );
	
	wxBoxSizer* bSizer32;
	bSizer32 = new wxBoxSizer( wxHORIZONTAL );
	
	BL = new wxButton( m_panel1, wxID_ANY, wxEmptyString, wxDefaultPosition, wxDefaultSize, 0 );
	bSizer32->Add( BL, 0, wxALL, 5 );
	
	BM = new wxButton( m_panel1, wxID_ANY, wxEmptyString, wxDefaultPosition, wxDefaultSize, 0 );
	bSizer32->Add( BM, 0, wxALL, 5 );
	
	BR = new wxButton( m_panel1, wxID_ANY, wxEmptyString, wxPoint( -1,-1 ), wxDefaultSize, 0 );
	bSizer32->Add( BR, 0, wxALL, 5 );
	
	bSizer2->Add( bSizer32, 0, wxALIGN_CENTER_HORIZONTAL, 5 );
	
	wxBoxSizer* bSizer6;
	bSizer6 = new wxBoxSizer( wxHORIZONTAL );
	
	resetButton = new wxButton( m_panel1, wxID_ANY, wxT("Reset"), wxDefaultPosition, wxDefaultSize, 0 );
	bSizer6->Add( resetButton, 0, wxALL, 5 );
	
	exitButton = new wxButton( m_panel1, wxID_ANY, wxT("Exit"), wxDefaultPosition, wxDefaultSize, 0 );
	bSizer6->Add( exitButton, 0, wxALL, 5 );
	
	bSizer2->Add( bSizer6, 0, wxALIGN_CENTER_HORIZONTAL, 5 );
	
	m_panel1->SetSizer( bSizer2 );
	m_panel1->Layout();
	bSizer2->Fit( m_panel1 );
	bSizer1->Add( m_panel1, 1, wxEXPAND, 5 );
	
	this->SetSizer( bSizer1 );
	this->Layout();
	
	this->Centre( wxBOTH );
	
	// Connect Events
	this->Connect( menuItem->GetId(), wxEVT_COMMAND_MENU_SELECTED, wxCommandEventHandler( GUIClass::OnExitSelected ) );
	TL->Connect( wxEVT_COMMAND_BUTTON_CLICKED, wxCommandEventHandler( GUIClass::OnTTTButtonClick ), NULL, this );
	TM->Connect( wxEVT_COMMAND_BUTTON_CLICKED, wxCommandEventHandler( GUIClass::OnTTTButtonClick ), NULL, this );
	TR->Connect( wxEVT_COMMAND_BUTTON_CLICKED, wxCommandEventHandler( GUIClass::OnTTTButtonClick ), NULL, this );
	ML->Connect( wxEVT_COMMAND_BUTTON_CLICKED, wxCommandEventHandler( GUIClass::OnTTTButtonClick ), NULL, this );
	MM->Connect( wxEVT_COMMAND_BUTTON_CLICKED, wxCommandEventHandler( GUIClass::OnTTTButtonClick ), NULL, this );
	MR->Connect( wxEVT_COMMAND_BUTTON_CLICKED, wxCommandEventHandler( GUIClass::OnTTTButtonClick ), NULL, this );
	BL->Connect( wxEVT_COMMAND_BUTTON_CLICKED, wxCommandEventHandler( GUIClass::OnTTTButtonClick ), NULL, this );
	BM->Connect( wxEVT_COMMAND_BUTTON_CLICKED, wxCommandEventHandler( GUIClass::OnTTTButtonClick ), NULL, this );
	BR->Connect( wxEVT_COMMAND_BUTTON_CLICKED, wxCommandEventHandler( GUIClass::OnTTTButtonClick ), NULL, this );
	resetButton->Connect( wxEVT_COMMAND_BUTTON_CLICKED, wxCommandEventHandler( GUIClass::OnResetButtonClick ), NULL, this );
	exitButton->Connect( wxEVT_COMMAND_BUTTON_CLICKED, wxCommandEventHandler( GUIClass::OnExitButtonClick ), NULL, this );
}

GUIClass::~GUIClass()
{
	// Disconnect Events
	this->Disconnect( wxID_ANY, wxEVT_COMMAND_MENU_SELECTED, wxCommandEventHandler( GUIClass::OnExitSelected ) );
	TL->Disconnect( wxEVT_COMMAND_BUTTON_CLICKED, wxCommandEventHandler( GUIClass::OnTTTButtonClick ), NULL, this );
	TM->Disconnect( wxEVT_COMMAND_BUTTON_CLICKED, wxCommandEventHandler( GUIClass::OnTTTButtonClick ), NULL, this );
	TR->Disconnect( wxEVT_COMMAND_BUTTON_CLICKED, wxCommandEventHandler( GUIClass::OnTTTButtonClick ), NULL, this );
	ML->Disconnect( wxEVT_COMMAND_BUTTON_CLICKED, wxCommandEventHandler( GUIClass::OnTTTButtonClick ), NULL, this );
	MM->Disconnect( wxEVT_COMMAND_BUTTON_CLICKED, wxCommandEventHandler( GUIClass::OnTTTButtonClick ), NULL, this );
	MR->Disconnect( wxEVT_COMMAND_BUTTON_CLICKED, wxCommandEventHandler( GUIClass::OnTTTButtonClick ), NULL, this );
	BL->Disconnect( wxEVT_COMMAND_BUTTON_CLICKED, wxCommandEventHandler( GUIClass::OnTTTButtonClick ), NULL, this );
	BM->Disconnect( wxEVT_COMMAND_BUTTON_CLICKED, wxCommandEventHandler( GUIClass::OnTTTButtonClick ), NULL, this );
	BR->Disconnect( wxEVT_COMMAND_BUTTON_CLICKED, wxCommandEventHandler( GUIClass::OnTTTButtonClick ), NULL, this );
	resetButton->Disconnect( wxEVT_COMMAND_BUTTON_CLICKED, wxCommandEventHandler( GUIClass::OnResetButtonClick ), NULL, this );
	exitButton->Disconnect( wxEVT_COMMAND_BUTTON_CLICKED, wxCommandEventHandler( GUIClass::OnExitButtonClick ), NULL, this );
	
}
