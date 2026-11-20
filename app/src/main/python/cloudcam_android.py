"""
Minimal Python bridge for CTVGRK Android app.
This is a stub that will later call the real cloudcam library.

For the first version we only implement a safe mock + structure
so the app can build and run without full cloudcam integration yet.
"""

import json
import traceback


def list_cameras(email: str, password: str, platform: str = "ezviz") -> str:
    """
    Returns a JSON string of cameras.
    Later this will call the real cloudcam library.
    """
    try:
        # --- TEMPORARY MOCK ---
        # Replace this block with real cloudcam calls once the library is integrated.
        if not email or not password:
            return json.dumps([{"serial": "-", "name": "Email/password kosong", "channel": 0, "status": "error"}])

        # Simulated successful response
        cameras = [
            {
                "serial": "DEMO123456",
                "name": f"Demo Camera 1 ({platform})",
                "channel": 1,
                "status": "online"
            },
            {
                "serial": "DEMO123456",
                "name": f"Demo Camera 2 ({platform})",
                "channel": 2,
                "status": "online"
            },
            {
                "serial": "DEMO789012",
                "name": "Front Gate",
                "channel": 1,
                "status": "online"
            }
        ]
        return json.dumps(cameras)

        # --- REAL IMPLEMENTATION (later) ---
        # from cloudcam import CloudCam
        # import os
        # os.environ["CLOUDCAM_PLATFORM"] = platform
        # os.environ["CLOUDCAM_EMAIL"] = email
        # os.environ["CLOUDCAM_PASSWORD"] = password
        # cam = CloudCam()
        # devices = cam.cameras()
        # result = []
        # for d in devices:
        #     result.append({
        #         "serial": getattr(d, "serial", str(d)),
        #         "name": getattr(d, "name", "Camera"),
        #         "channel": getattr(d, "channel", 1),
        #         "status": "online" if getattr(d, "online", True) else "offline"
        #     })
        # return json.dumps(result)

    except Exception as e:
        return json.dumps([{
            "serial": "-",
            "name": f"Error: {str(e)}",
            "channel": 0,
            "status": "error",
            "trace": traceback.format_exc()
        }])


def ping() -> str:
    """Simple health check from Kotlin."""
    return "ok"
