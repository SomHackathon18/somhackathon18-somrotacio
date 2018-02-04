import os
import random

from datetime import datetime
from flask import logging

from persistence import Persistence

abs_path = os.path.dirname(os.path.abspath(__file__))
db = os.path.join(abs_path, os.environ['DB_PATH'])
repository = Persistence(db, logging.getLogger(__name__))

repository.create(vehicle=random.randint(0, 500), parkingArea=178, startTime=datetime.now(), tipusVehicle=random.randint(0, 2))
repository.create(vehicle=random.randint(0, 500), parkingArea=178, startTime=datetime.now(), tipusVehicle=random.randint(0, 2))
repository.create(vehicle=random.randint(0, 500), parkingArea=178, startTime=datetime.now(), tipusVehicle=random.randint(0, 2))
repository.create(vehicle=random.randint(0, 500), parkingArea=178, startTime=datetime.now(), tipusVehicle=random.randint(0, 2))
repository.create(vehicle=random.randint(0, 500), parkingArea=313, startTime=datetime.now(), tipusVehicle=random.randint(0, 2))
repository.create(vehicle=random.randint(0, 500), parkingArea=313, startTime=datetime.now(), tipusVehicle=random.randint(0, 2))
repository.create(vehicle=random.randint(0, 500), parkingArea=313, startTime=datetime.now(), tipusVehicle=random.randint(0, 2))
repository.create(vehicle=random.randint(0, 500), parkingArea=313, startTime=datetime.now(), tipusVehicle=random.randint(0, 2))
repository.create(vehicle=random.randint(0, 500), parkingArea=466, startTime=datetime.now(), tipusVehicle=random.randint(0, 2))
repository.create(vehicle=random.randint(0, 500), parkingArea=466, startTime=datetime.now(), tipusVehicle=random.randint(0, 2))
repository.create(vehicle=random.randint(0, 500), parkingArea=314, startTime=datetime.now(), tipusVehicle=random.randint(0, 2))
repository.create(vehicle=random.randint(0, 500), parkingArea=314, startTime=datetime.now(), tipusVehicle=random.randint(0, 2))
repository.create(vehicle=random.randint(0, 500), parkingArea=314, startTime=datetime.now(), tipusVehicle=random.randint(0, 2))
repository.create(vehicle=random.randint(0, 500), parkingArea=173, startTime=datetime.now(), tipusVehicle=random.randint(0, 2))
repository.create(vehicle=random.randint(0, 500), parkingArea=173, startTime=datetime.now(), tipusVehicle=random.randint(0, 2))
repository.create(vehicle=random.randint(0, 500), parkingArea=173, startTime=datetime.now(), tipusVehicle=random.randint(0, 2))
