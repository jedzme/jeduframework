#include <File.au3>
;~ #include <MsgBoxConstants.au3>

Local $sFileToBeUploaded = _PathFull(@ScriptDir & "\fileupload.exe")
;~ MsgBox($MB_SYSTEMMODAL, "", $sFileToBeUploaded)

WinWait("Open", "" , 10)
ControlFocus("Open", "", "Edit1")
ControlSetText("Open", "", "Edit1", $sFileToBeUploaded)
ControlClick("Open", "", "Button1")