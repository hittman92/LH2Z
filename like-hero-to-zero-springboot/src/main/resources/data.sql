-- Länder:

INSERT INTO COUNTRY (id, name, region)
VALUES ('DEU','Germany','Europe')
ON DUPLICATE KEY UPDATE name=VALUES(name), region=VALUES(region);

INSERT INTO COUNTRY (id, name, region)
VALUES ('FRA','France','Europe')
ON DUPLICATE KEY UPDATE name=VALUES(name), region=VALUES(region);

INSERT INTO COUNTRY (id, name, region)
VALUES ('USA','United States','North America')
ON DUPLICATE KEY UPDATE name=VALUES(name), region=VALUES(region);

INSERT INTO COUNTRY (id, name, region)
VALUES ('CHN','China','Asia')
ON DUPLICATE KEY UPDATE name=VALUES(name), region=VALUES(region);

INSERT INTO COUNTRY (id, name, region)
VALUES ('IND','India','Asia')
ON DUPLICATE KEY UPDATE name=VALUES(name), region=VALUES(region);

INSERT INTO COUNTRY (id, name, region)
VALUES ('BRA','Brazil','South America')
ON DUPLICATE KEY UPDATE name=VALUES(name), region=VALUES(region);

-- Beispiel-Emissionen:

INSERT INTO EMISSION_RECORD (country_id, year_, value_kt, source, status, created_at)
SELECT 'DEU', 2021, 700000, 'WorldBank', 'APPROVED', NOW(6)
FROM DUAL
WHERE NOT EXISTS (
  SELECT 1 FROM EMISSION_RECORD
  WHERE country_id='DEU' AND year_=2021 AND source='WorldBank'
);

INSERT INTO EMISSION_RECORD (country_id, year_, value_kt, source, status, created_at)
SELECT 'FRA', 2021, 450000, 'WorldBank', 'APPROVED', NOW(6)
FROM DUAL
WHERE NOT EXISTS (
  SELECT 1 FROM EMISSION_RECORD
  WHERE country_id='FRA' AND year_=2021 AND source='WorldBank'
);

INSERT INTO EMISSION_RECORD (country_id, year_, value_kt, source, status, created_at)
SELECT 'USA', 2021, 5200000, 'WorldBank', 'APPROVED', NOW(6)
FROM DUAL
WHERE NOT EXISTS (
  SELECT 1 FROM EMISSION_RECORD
  WHERE country_id='USA' AND year_=2021 AND source='WorldBank'
);

INSERT INTO EMISSION_RECORD (country_id, year_, value_kt, source, status, created_at)
SELECT 'CHN', 2021, 10000000, 'WorldBank', 'APPROVED', NOW(6)
FROM DUAL
WHERE NOT EXISTS (
  SELECT 1 FROM EMISSION_RECORD
  WHERE country_id='CHN' AND year_=2021 AND source='WorldBank'
);

INSERT INTO EMISSION_RECORD (country_id, year_, value_kt, source, status, created_at)
SELECT 'IND', 2021, 2700000, 'WorldBank', 'APPROVED', NOW(6)
FROM DUAL
WHERE NOT EXISTS (
  SELECT 1 FROM EMISSION_RECORD
  WHERE country_id='IND' AND year_=2021 AND source='WorldBank'
);

INSERT INTO EMISSION_RECORD (country_id, year_, value_kt, source, status, created_at)
SELECT 'BRA', 2021, 490000, 'WorldBank', 'APPROVED', NOW(6)
FROM DUAL
WHERE NOT EXISTS (
  SELECT 1 FROM EMISSION_RECORD
  WHERE country_id='BRA' AND year_=2021 AND source='WorldBank'
);