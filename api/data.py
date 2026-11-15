import copy
import uuid

BOOKING_SCHEMA = {
    "type": "object",
    "required": ["firstname", "lastname", "totalprice", "depositpaid", "bookingdates"],
    "properties": {
        "firstname": {"type": "string"},
        "lastname": {"type": "string"},
        "totalprice": {"type": "integer"},
        "depositpaid": {"type": "boolean"},
        "bookingdates": {
            "type": "object",
            "required": ["checkin", "checkout"],
            "properties": {
                "checkin": {"type": "string", "pattern": r"^\d{4}-\d{2}-\d{2}$"},
                "checkout": {"type": "string", "pattern": r"^\d{4}-\d{2}-\d{2}$"},
            },
        },
        "additionalneeds": {"type": "string"},
    },
}

CREATED_SCHEMA = {
    "type": "object",
    "required": ["bookingid", "booking"],
    "properties": {"bookingid": {"type": "integer"}, "booking": BOOKING_SCHEMA},
}


def booking(**overrides):
    """Booking valid dengan nama unik, supaya test tidak saling bertabrakan di server bersama."""
    data = {
        "firstname": "Rivaldi",
        "lastname": f"QA-{uuid.uuid4().hex[:8]}",
        "totalprice": 150,
        "depositpaid": True,
        "bookingdates": {"checkin": "2026-12-01", "checkout": "2026-12-05"},
        "additionalneeds": "Breakfast",
    }
    for k, v in overrides.items():
        data[k] = v
    return data


def without(data: dict, dotted_key: str) -> dict:
    data = copy.deepcopy(data)
    *path, last = dotted_key.split(".")
    target = data
    for p in path:
        target = target[p]
    del target[last]
    return data
