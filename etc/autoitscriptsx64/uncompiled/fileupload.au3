#include <File.au3>
;~ #include <MsgBoxConstants.au3>

Local $sFileToBeUploaded = _PathFull(@ScriptDir & "\fileupload.exe")
;~ MsgBox($MB_SYSTEMMODAL, "", $sFileToBeUploaded)

$openFlag = WinWait("Open", "" , 10)

If $openFlag <> 0 Then
   ControlFocus("Open", "", "Edit1")
   ControlSetText("Open", "", "Edit1", $sFileToBeUploaded)
   ControlClick("Open", "", "Button1")
EndIf