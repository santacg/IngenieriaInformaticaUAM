CREATE OR REPLACE FUNCTION updOrdersFunction() 
RETURNS TRIGGER AS $$
BEGIN
    -- Actualiza orders después de insertar, actualizar o eliminar en orderdetail
    IF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN
        -- Actualiza las cantidades en orders cuando se inserta o actualiza un registro en orderdetail
        UPDATE orders
        SET 
            netamount = (SELECT SUM(price * quantity) FROM orderdetail WHERE orderid = NEW.orderid),
            totalamount = (SELECT SUM(price * quantity) FROM orderdetail WHERE orderid = NEW.orderid) + (SELECT tax FROM orders WHERE orderid = NEW.orderid)
        WHERE orderid = NEW.orderid;
    ELSIF (TG_OP = 'DELETE') THEN
        -- Actualiza las cantidades en orders cuando se elimina un registro en orderdetail
        UPDATE orders
        SET 
            netamount = (SELECT SUM(price * quantity) FROM orderdetail WHERE orderid = OLD.orderid),
            totalamount = (SELECT SUM(price * quantity) FROM orderdetail WHERE orderid = OLD.orderid) + (SELECT tax FROM orders WHERE orderid = OLD.orderid)
        WHERE orderid = OLD.orderid;
    END IF;
    -- Devuelve el registro modificado
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER updOrders
AFTER INSERT OR UPDATE OR DELETE ON orderdetail
FOR EACH ROW EXECUTE FUNCTION updOrdersFunction();
